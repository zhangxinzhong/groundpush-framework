(function () {
    window.Utils = {
        /**
         * 判断是否是数组
         *
         * @param {Array} 数组
         */
        time: function () {
            return (new Date).getTime();
        },
        isArray: ('isArray' in Array) ? Array.isArray : function (value) {
            return toString.call(value) === '[object Array]';
        },
        /**
         * 判断数据是否是空
         * @param obj
         * @returns {boolean}
         */
        isEmptyArray: function (obj) {
            if (Utils.isArray(obj)) {
                return obj.length == 0;
            }
            return false;
        },
        /**
         * 判断对象是否是{}
         */
        isEmptyObject: function (obj) {
            for (var name in obj) {
                return false; //因__proto__无法获取故无属性可轮循
            }
            return true;
        },
        /**
         * 判断是否是对象
         *
         * @param {Object} jsonObject
         */
        isObject: (toString.call(null) === '[object Object]') ? function (value) { // ie
            return value !== null && value !== undefined && toString.call(value) === '[object Object]' && value.ownerDocument === undefined;
        } : function (value) { // not ie
            return toString.call(value) === '[object Object]';
        },
        /**
         * 判断是否是function对象
         */
        isFunction:
        // Safari 3.x and 4.x returns 'function' for typeof <NodeList>, hence we need to fall back to using
        // Object.prorotype.toString (slower)
            (typeof document !== 'undefined' && typeof document.getElementsByTagName('body') === 'function') ? function (
                value) {
                return toString.call(value) === '[object Function]';
            } : function (value) {
                return typeof value === 'function';
            },
        /**
         * Returns true if the passed value is a string.
         *
         * @param {Mixed} value The value to test
         * @return {Boolean}
         */
        isString: function (value) {
            return typeof value === 'string';
        },
        isDefined: function (value) {
            return typeof value !== 'undefined';
        },
        isEmpty: function (value) {
            return value == null || value === undefined || value === "";
        },
        isBoolean: function (value) {
            return typeof value === 'boolean';
        },
        isNumber: function (value) {
            return typeof value === 'number' && isFinite(value);
        },
        urlAppend: function (url, s) {
            if (!Cis.isEmpty(s)) {
                return url + (url.indexOf('?') === -1 ? '?' : '&') + s;
            }
            return url;
        },
        /**
         * 类式继承 只继承了方法，lzh
         * @param {Function} subclass 子类
         * @param {Function} superClass 父类
         * @param {Object} obj 向子类中添加的公共方法
         */
        extend: function (subClass, superClass, obj) {
            var F = function () {
            };
            F.prototype = superClass.prototype;
            subClass.prototype = new F(); // 子类现在已经没有了自己的方法
            // 把obj中的方法添加到子类的prototype中去，覆盖子类中同名的方法（从父类继承过来的方法）
            if (Cis.isDefined(obj)) {
                for (name in obj) {
                    subClass.prototype[name] = obj[name]; // 父类中的方法将覆盖子类中的方法
                }
            }
            subClass.prototype.constructor = subClass;
            subClass.superclass = superClass.prototype; // 记录父类的原型，方便调用父类的方法
            if (superClass.prototype.constructor == Object.prototype.constructor) {
                // 对superClass的prototype赋值，添加公共函数，会改变constructor的指向
                superClass.prototype.constructor = superClass;
            }
        },
        rmoney: function (e) {
            return parseFloat(e.replace(/[^\d\.-]/g, ""));
        },

        /**
         * AJAX请求公用方法
         * @param url 后台请求地址
         * @param data 参数
         * @param map index
         * @param callBack
         * @param errorBack
         */
        postLoginAjax: function (url, data, callBack, errorBack) {
            console.log(url, data);
            $.ajax({
                type: 'post',
                async: false,
                data: data,
                dataType: 'json',
                url: Utils.encode(url),
                contentType: 'application/x-www-form-urlencoded',
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if (Utils.verifyFunction(errorBack)) {
                        errorBack(data, XMLHttpRequest, textStatus, errorThrown);
                    }
                },
                success: function (data) {
                    if (Utils.verifyFunction(callBack)) {
                        callBack(data);
                    }
                }
            });
        },

        /**
         * AJAX请求公用方法
         * @param url 后台请求地址
         * @param data 参数
         * @param asyncFlag false 同步加载,true异步加载
         * @param map index
         * @param callBack
         * @param errorBack
         */
        postAjax: function (url, data, callBack, errorBack) {
            console.log(url, data);
            $.ajax({
                type: 'post',
                async: false,
                data: data,
                dataType: 'json',
                url: Utils.encode(url),
                contentType: 'application/json',
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if (Utils.verifyFunction(errorBack)) {
                        errorBack(data, XMLHttpRequest, textStatus, errorThrown);
                    }
                },
                success: function (data) {
                    if (Utils.verifyFunction(callBack)) {
                        callBack(data);
                    }
                }
            });
        },

        /**
         * AJAX请求公用方法
         * @param url 后台请求地址
         * @param data 参数
         * @param asyncFlag false 同步加载,true异步加载
         * @param map index
         * @param callBack
         * @param errorBack
         */
        putAjax: function (url, data, callBack, errorBack) {
            console.log(url, data);
            $.ajax({
                type: 'put',
                async: false,
                data: data,
                dataType: 'json',
                url: Utils.encode(url),
                contentType: 'application/json',
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if (Utils.verifyFunction(errorBack)) {
                        errorBack(data, XMLHttpRequest, textStatus, errorThrown);
                    }
                },
                success: function (data) {
                    if (Utils.verifyFunction(callBack)) {
                        callBack(data);
                    }
                }
            });
        },

        /**
         * AJAX请求公用方法
         * @param url 后台请求地址
         * @param data 参数
         * @param asyncFlag false 同步加载,true异步加载
         * @param map index
         * @param callBack
         * @param errorBack
         */
        getAjax: function (url, data, callBack, errorBack) {

            $.ajax({
                type: 'get',
                cache: false,// 从页面缓存获取加载信息
                async: false,
                dataType: 'json',
                url: Utils.encode(url),
                data: data,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if (Utils.verifyFunction(errorBack)) {
                        errorBack(data, XMLHttpRequest, textStatus, errorThrown);
                    }
                },
                success: function (data) {
                    if (Utils.verifyFunction(callBack)) {
                        callBack(data);
                    }
                }
            });
        },

        /**
         * AJAX请求公用方法
         * @param url 后台请求地址
         * @param data 参数
         * @param asyncFlag false 同步加载,true异步加载
         * @param map index
         * @param callBack
         * @param errorBack
         */
        deleteAjax: function (url, data, callBack, errorBack) {

            $.ajax({
                type: 'delete',
                cache: false,// 从页面缓存获取加载信息
                async: false,
                dataType: 'json',
                url: Utils.encode(url),
                data: data,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if (Utils.verifyFunction(errorBack)) {
                        errorBack(data, XMLHttpRequest, textStatus, errorThrown);
                    }
                },
                success: function (data) {
                    if (Utils.verifyFunction(callBack)) {
                        callBack(data);
                    }
                }
            });
        },

        /**
         * 转码
         * @param url
         * @returns {string}
         */
        encode(url) {
            return encodeURI(encodeURI(url));
        },

        /**
         * 验证是否是function
         * @param call
         * @returns {boolean}
         */
        verifyFunction(call) {
            if (typeof (call) == 'function') {
                return true;
            }
            return false;
        }
    };

    /**
     * 对字符串的一些扩展
     */
    Utils.String = {
        trimRegex: /^[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u2028\u2029\u202f\u205f\u3000]+|[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u2028\u2029\u202f\u205f\u3000]+$/g,
        ellipsis: function (value, len, word) {
            if (value && value.length > len) {
                if (word) {
                    var vs = value.substr(0, len - 2),
                        index = Math.max(vs.lastIndexOf(' '), vs.lastIndexOf('.'), vs.lastIndexOf('!'), vs
                            .lastIndexOf('?'));
                    if (index !== -1 && index >= (len - 15)) {
                        return vs.substr(0, index) + "...";
                    }
                }
                return value.substr(0, len - 3) + "...";
            }
            return value;
        },
        trim: function (string) {
            return string.replace(Utils.String.trimRegex, "");
        },
        htmlEncode: (function () {
            var entities = {
                    '&': '&amp;',
                    '>': '&gt;',
                    '<': '&lt;',
                    '"': '&quot;'
                },
                keys = [],
                p, regex;
            for (p in entities) {
                keys.push(p);
            }
            regex = new RegExp('(' + keys.join('|') + ')', 'g');
            return function (value) {
                return (!value) ? value : String(value).replace(regex, function (match, capture) {
                    return entities[capture];
                });
            };
        })(),
        /**
         * Capitalize the given string
         * @param {String} string
         * @return {String}
         */
        capitalize: function (string) {
            return string.charAt(0).toUpperCase() + string.substr(1);
        }
    };
    /**
     * 替换所有匹配exp的字符串为指定字符串
     * @param exp 被替换部分的正则
     * @param newStr 替换成的字符串
     */
    String.prototype.replaceAll = function (exp, newStr) {
        return this.replace(new RegExp(exp, "gm"), newStr);
    };

    /**
     * 原型：字符串格式化
     * @param args 格式化参数值
     */
    String.prototype.format = function (args) {
        var result = this;
        if (arguments.length < 1) {
            return result;
        }

        var data = arguments; // 如果模板参数是数组
        if (arguments.length == 1 && typeof (args) == "object") {
            // 如果模板参数是对象
            data = args;
        }
        for (var key in data) {
            var value = data[key];
            if (undefined != value) {
                result = result.replaceAll("\\{" + key + "\\}", value);
            }
        }
        return result;
    };

    Number.prototype.format = function (decimals, dec_point, thousands_sep) {
        /*
         　　 * 参数说明：
         　　 * decimals：保留几位小数
         　　 * dec_point：小数点符号
         　　 * thousands_sep：千分位符号
         　　 * */
        var number = this;
        number = (number + '').replace(/[^0-9+-Ee.]/g, '');
        var n = !isFinite(+number) ? 0 : +number,
            prec = !isFinite(+decimals) ? 2 : Math.abs(decimals),
            sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
            dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
            s = '',
            toFixedFix = function (n, prec) {
                var k = Math.pow(10, prec);
                return '' + Math.round(n * k) / k;
            };

        s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
        var re = /(-?\d+)(\d{3})/;
        while (re.test(s[0])) {
            s[0] = s[0].replace(re, "$1" + sep + "$2");
        }

        if ((s[1] || '').length < prec) {
            s[1] = s[1] || '';
            s[1] += new Array(prec - s[1].length + 1).join('0');
        }
        return s.join(dec);
    };

    /**
     * 文件大小格式化
     * @param args
     * @returns {String}
     */
    String.prototype.sizeformat = function () {
        var num = this;
        if (arguments.length < 0) {
            return '0KB';
        }
        num = Number(num);
        var isLowerZero = false;
        if (num < 0) {
            isLowerZero = true;
            num = -num;
        }
        if (!num || !window.Utils.isNumber(num))
            return '';
        var returnValue = 0;
        if (parseInt(num / 1048576) < 1) {
            returnValue = ((num / 1024).toFixed(2) + 'KB').replace(/.00KB/, 'KB');
        }
        if (parseInt(num / 1073741824) < 1) {
            returnValue = ((num / 1048576).toFixed(2) + 'MB').replace(/.00MB/, 'MB');
        }
        if (parseInt(num / (1073741824 * 1024)) < 1) {
            returnValue = ((num / 1073741824).toFixed(2) + 'G').replace(/.00G/, 'G');
        }
        if (parseInt(num / (1073741824 * 1024 * 1024)) < 1) {
            returnValue = ((num / (1073741824 * 1024)).toFixed(2) + 'T').replace(/.00T/, 'T');
        }
        if (isLowerZero) {
            return "-" + returnValue;
        }
        return returnValue;
    }

    /**
     * 时间格式化
     * @param format
     * @returns {*}
     */
    Date.prototype.format = function (format) {
        /*
         * format="yyyy-MM-dd HH:mm:ss";
         */
        var o = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "H+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),
            "S": this.getMilliseconds()
        }
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }

    window.DynamciLoadUtil = {
        loadJS: function (url, callback) {
            let script = document.createElement('script');
            script.type = 'text/javascript';
            script.src = url;
            if (typeof callback == 'function') {
                script.onload = script.onreadystatechange = function () {
                    if (!this.readyState || this.readyState == 'loaded'
                        || this.readyState == 'complete') {
                        callback();
                        script.onload = script.onreadystatechange = null;
                    }
                }
            }
            document.body.appendChild(script);
        },
        loadJSText: function (jsText) {
            let script = document.createElement('script');
            script.type = 'text/javascript';
            try {
                // Firefox,Safari,Chrome,Opera支持
                script.appendChild(document.createTextNode(jsText));
            } catch (ex) {
                script.text = jsText;
            }
            document.body.appendChild(script);
        },
        // 动态加载外部CSS文件
        loadCSS: function (url) {
            let link = document.createElement('link');
            link.rel = 'stylesheet';
            link.type = 'text/css';
            link.url = url;
            document.getElementsByTagName('head')[0].appendChild(link);
        },
        loadCSSText: function (cssText) {
            let style = document.createElement('style');
            style.type = 'text/css';
            try {
                // Firefox,Safari,Chrome,Opera支持
                style.appendChild(document.createTextNode(cssText));
            } catch (ex) {
                // IE早期浏览器，需要使用style元素的styleSheet属性的cssText属性
                style.styleSheet.cssText = cssText;
            }
        }
    };
}());