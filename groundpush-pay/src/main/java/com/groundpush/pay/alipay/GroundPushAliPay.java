package com.groundpush.pay.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groundpush.pay.exception.PayException;
import com.groundpush.pay.model.AliPayRequest;
import com.groundpush.pay.model.AliPayRsponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @description: 支付宝提现
 * @author: zhangxinzhong
 * @date: 2019-09-07 上午10:50
 */
@Slf4j
@Component
public class GroundPushAliPay {

    private static final String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC5J0tPDlmkxHZJk4CiI6a7xpo7C9FIYVFBauO5lOVpFXlOOj470psljaaktbaUeaRwb0/HPRMkP/U5ctnWhA7qNZwh4QpDrZjBbpSstIcFwz07vz40TOqPZZK2F7S4tAPC2CzbKxBvE+Lnsvo4dfOdOFwM7HLAyA2lsOrMs+BFIf7oshezSX/0+ARcCdZFLQy58huh0u7weWuSBLldMs/Vxj8vs9J1UKdud0IrW+N3N5ug7cf0E7EpvHzG6gWpiEFz8lCAJbsdEHEYPdovxCGJ0aWbvFYJkt5QeUhfakJvHkl8atLYIocd9PFKlsf+dZsQNgmlg7H0pbArTM0uFikXAgMBAAECggEAH3D9x+H+9QzrrVjf+iilsrNqE2bRVkI4Yyx8mVIglvSvkcowMUZ1VC42wzecVqBFfVZZrNLBQmGeCfBn75ajVcBe0B3/+TgsfYSCuMU7PI5IArJaJNV+63TrMgLajJb9IIRHMbyhYMt2t7Pb6+STxvoAH62zWutd7eoaJULSbcqrGgHNGrmtIlPIp06Kaqwt9EzMhuM7gaWuFaE27FdMYAXPk51/xgfMLshujeo/Yp+6SgUvKvotAOk5UX/op93YqbS2KeYOCVEAo/HcBfQaF+lyWo0oIX+A6XYSOfHagplrnFn9GONunYlKTg3OaxZN1+7yibOGfTZiH7vhQnbzAQKBgQDh1i6JHULhdZ1tVEuebtW0gmiWM6K/XzY74Gwn3NGnNZicA8m1eyCZXj/CMVaTZtdWTstf1tdHIe79UjdW6aCEWysG7z7Ks3xFciHeAcpToZzsq0UtTmexiCKvWyVzFEU2cffA7GqlxTElaWRd4Uazh7eFB4j74VdNJc1bfJ2AgQKBgQDR4hJnH7QiPEGQLFv6k1Hg47hWExQ6Z5OoJzQIXjcL+shdUyIAX1d0QI4NZBGojzBVqKM98jCFp7DsRCgQLB9kxpSLu0S/J48z7l+Fw77HEym+b+Pg4LfIWjk1Bb04VHpMHc3gGWPWvjurI3dGRx+/P+Dv143nH69KYfJYFUDdlwKBgFYP+DX6d7VRzAeQ+yxKUphypgavcNL7JsDUuoLKbP8Ktril3OWTIY4w051ejf6oT2Gtr2U7i1ipPtk8zqgJaklmMNR4sfYvRil04CEpj4Alc5bhggBNA8ks9wLaekALtoCUF/VmYxH/PsZiDoSUSAw8qRxCDdrnOgzFXigCKkeBAoGAU8IuqezjDE6Ts5+gTU2BfYgjRmZyyiQPrymGAFjpCuTYFTZZ+WATHJeumPV4fpY2KdkU3GHMr2oOlxtUCbAyDxuYqHLhqo90/LqCcHV/qx6gqBkrDdFgRpqK/ff+XX6JF9tFD8vSJh7g3RlYPjmgF8i8UbrqLHRelgakmjZRHp0CgYBpmdv/HrHxsm1yLjfHFD+oBtxODE3y3DSO3IGBqSuSOWRDtD6iupyfKacinB5X1YrseDKOVwxCB2yaOLc9uC9seTNPkOsFUAEaBjPIYsqh3xg1YMfuPKinagmYHs7kNVOMQEyOsSBg3GYzCU/v/zIAn+6wgTAf43omg02DJoswWg==";

    private static final String aliPayKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg75W6qd3rIPxRSFUl7YsJTnXFFcJppbcjXs2+2cuCIrxG2q8WiEkTyKKtdj2uCuFPVVlbBaqvcScnfABS0WclX6GQgdCd4zLsVZKXa1T++42o9CzSPc9cVQ/zU5H2uhuwrOm/0b+cHJ8g9Sk39qNiFPGeULvHkltq+2oofJ2Tt4dkEA7n1/RkIjMQecG+WyKnTWoGLBlp4PVYUijq3LruKIaK8dfgFb3ImqZSrIkN8hOAIrOIbyMFFd3A7zPYYxomYHVWpPxx3s3xTthGCyaZLXjqLVtySuSsSk7KzXPErHmv6f2qR6zyj8dkwL6UuL8uWIjjjUpi8pPztr7ehtK+wIDAQAB";

    @Resource
    private ObjectMapper objectMapper;

    /**
     * private_key 是通过 AlipayDevelopmentAssistant-1.0.0  【商户应用私钥】
     * alipay_public_key 是通过 账户中心-秘钥管理-开放平台秘钥-路客地推-接口加签方式-支付宝公钥
     * 支付宝支付
     */
    public Optional<AliPayRsponse> pay(AliPayRequest aliPayRequest) {
        try {
            AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "2019090767048287", privateKey, "json", "UTF-8", aliPayKey, "RSA2");
            AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
            request.setBizContent(objectMapper.writeValueAsString(aliPayRequest));
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            AliPayRsponse aliPayRsponse = AliPayRsponse.builder().code(response.getCode()).message(response.getMsg()).subCode(response.getSubCode()).subMessage(response.getSubMsg()).outBizNo(response.getOutBizNo()).OrderId(response.getOrderId()).payDate(LocalDateTime.now()).build();
            return Optional.of(aliPayRsponse);
        } catch (AlipayApiException e) {
            log.error(e.getMessage(), e);
            throw new PayException("提现失败");
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new PayException("提现失败");
        }
    }
}
