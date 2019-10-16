package com.groundpush.core.mapper;

import com.github.pagehelper.Page;
import com.groundpush.core.condition.CustomerQueryCondition;
import com.groundpush.core.model.Customer;
import com.groundpush.core.model.Version;
import com.groundpush.core.vo.CustomerVo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @description: 版本信息mapper
 * @author: hengquan
 * @date: 2019-10-16 上午10:07
 */
public interface VersionMapper {

    Page<Version> queryVersionPage(Version version);

    Optional<Version> getVersion(Integer versionId);

    Boolean createVersion(Version version);
}
