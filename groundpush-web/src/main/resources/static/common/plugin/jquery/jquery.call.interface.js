/**
 * 公共方法
 * 
 * @param $type:参数传递方式
 * @param $url:请求路径
 * @param $data:请求参数
 * @param $async:数据加载方式
 * @param $response:返回数据
 */
function callInterface($type, $url, $data, $async) {
	// 调用接口
	var $response = {};
	$.ajax({
		type : $type,
		url : $url,
		data : $data,
		dataType : 'json',
		cache : false,// 从页面缓存获取加载信息
		async : $async, // false 同步加载,true异步加载
		beforeSend : function() {
		},
		success : function(response) {
			$response = response;
		},
		error : function(err) {
			return null;
		},
		complete : function() {
			$data = {};
		}
	});

	// 返回数据
	return $response;
}

/**
 * 提示异常
 */
var NO_LOGIN_MSG1 = "Expected session attribute 'myId'";
var NO_LOGIN_MSG2 = "令牌不存在";
function showErrorMsg(code, message) {
	if (code != 0) {
		var noLogin = false;
		if (message == NO_LOGIN_MSG1 || message == NO_LOGIN_MSG2 || message.indexOf('myId') > 0) {
			noLogin = true;
			message = "登录已超时";
		}
		if (noLogin || code < 3) {
			 // 询问框
			 var index = layer.confirm(message + ',请重新登录!', {
				 btn: ['确定','取消'] //按钮
			 }, function(){
				 layer.close(index);
				 Utils.toRedirect('login');
			 }, function(){
				 return;
			 });
		}
		else {
			if (message.length > 100) {
				message = "系统错误,请联系管理员!";
			}
			showAlert(message);
		}
	}
	return false;
}
