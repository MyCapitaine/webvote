/**
 * Created by hasee on 2017/4/14.
 */
$(document).ready(function() {

    console.log(previousPage);
    $("#email").emailMatch();
    // 隐藏Loading/登录失败 DIV
    $(".loading").hide();
    $(".login-error").hide();

    // 获取表单验证对象[填写验证规则]
    var validate = $("#signupForm").validate({
        rules : {
            loginName : {
                required : true,
                remote:{
                    url:"/isLoginNameUsed",
                    type:"POST",
                    data: {
                        loginName:function () {
                            console.log($("#loginName").val());
                            return $("#loginName").val();
                        }
                    }
                },
            },
            email : {
                required : true,
                email : true,
                remote:{
                    url:"/isEmailBinding",
                    type:"POST",
                    data: {
                        bindingEmail:function () {
                            console.log($("#email").val());
                            return $("#email").val();
                        }
                    }
                },
            },
            password : {
                required : true,
                minlength : 4,
                maxlength : 16
            }
        },
        messages : {
            loginName : {
                required : "请输入用户名",
                remote:"用户名已被占用"
            },
            email : {
                required : "请输入绑定邮箱",
                email : "邮箱格式不正确",
                remote:"邮箱已被占用"
            },
            password : {
                required : "请输入密码",
                minlength : "密码不少于4位",
                maxlength : "密码不多于16位"
            }
        }
    });

    // 输入框激活焦点、溢出焦点的渐变特效
    if ($("#email").val()) {
        $("#email").prev().fadeOut();
    }

    if ($("#loginName").val()) {
        $("#loginName").prev().fadeOut();
    }

    if ($("#password").val()) {
        $("#password").prev().fadeOut();
    }
    $("#email").focus(function() {
        $(this).prev().fadeOut();
    });
    $("#email").blur(function() {
        if (!$("#email").val()) {
            $(this).prev().fadeIn();
        }
    });
    $("#loginName").focus(function() {
        $(this).prev().fadeOut();
    });
    $("#loginName").blur(function() {
        if (!$("#loginName").val()) {
            $(this).prev().fadeIn();
        }
    });

    $("#password").focus(function() {
        $(this).prev().fadeOut();
    });
    $("#password").blur(function() {
        if (!$("#password").val()) {
            $(this).prev().fadeIn();
        }
    });

    // ajax提交登录
    $("#submit").bind("click", function() {
        login(validate);
    });

    $("body").each(function() {
        $(this).keydown(function() {
            if (event.keyCode == 13) {
                login(validate);
            }
        });
    });
});

function login(validate) {
    // 校验Email, password，校验如果失败的话不提交
    if (validate.form()) {
        //remeberUser.SetPwdAndChk();
        // var md5 = new MD5();
        // var f = md5.MD5($("#email").val()+$("#password").val()).toString();
        console.log($("#loginName").val());
        console.log($("#email").val());
        $.ajax({
            url : "/register",
            type : "post",
            data : {
                loginName : $("#loginName").val(),
                loginPassword : $("#password").val(),
                bindingEmail : $("#email").val()
            },
            dataType : "json",
            beforeSend : function() {
                $('.loading').show();
            },
            success : function(data) {
                $('.loading').hide();
                console.log(data);
                if (data.success) {
                    location.href="message";
                    // if(previousPage==null){
                    //     location.href="index";
                    // }
                    // else{
                    //     location.href=""+previousPage;
                    // }
                } else {
                    console.log(data.message);
                    if(data.message=="Email has been binding"){
                        var label=$("label[for='email']");
                        console.log(label);
                        label.html(data.message);
                        label.show();
                    }
                }
            }
        });
    }
}



/**
 * 记住用户类
 */
var RemeberUser = function() {
    this.GetLastUser();
};

RemeberUser.prototype.GetLastUser = function() {
    var id = "currentuser";
    var usr = this.GetCookie(id);
    if (usr != null) {
        $("#email").val(usr);
    }
    this.GetPwdAndChk();
};

// 点击登录时触发客户端事件
RemeberUser.prototype.SetPwdAndChk = function() {
    // 取用户名
    var usr = $("#email").val();
    // 将最后一个用户信息写入到Cookie
    this.SetLastUser(usr);
    // 如果记住密码选项被选中
    // var chk = $("#remeber").attr("checked");
    // if(chk == "checked")
    // {
    // //取密码值
    // var pwd = $("#password").val();
    // var expdate = new Date();
    // expdate.setTime(expdate.getTime() + 14 * (24 * 60 * 60 * 1000));
    // //将用户名和密码写入到Cookie
    // this.SetCookie(usr,pwd, expdate);
    // }
    // else
    // {
    // //如果没有选中记住密码,则立即过期
    // this.ResetCookie();
    // }
};
// 设置初始用户名
RemeberUser.prototype.SetLastUser = function(usr) {
    // var id = "49BAC005-3D3B-4231-8CEA-16939BEACD67";
    var id = "currentuser";
    var expdate = new Date();
    // 当前时间加上两周的时间
    expdate.setTime(expdate.getTime() + 14 * (24 * 60 * 60 * 1000));
    // TODO:this.SetCookie(id, usr, expdate,"/",".dataeye.com");
    this.SetCookie(id, usr, expdate);
};

// 用户名失去焦点时调用该方法
RemeberUser.prototype.GetPwdAndChk = function() {
    var usr = $("#email").val();
    var pwd = this.GetCookie("currenttoken");
    if (pwd != null) {
        // $("#password").val(pwd);
        $("#password").val("......");
    } else {
        $("#password").val("");
    }
};

// 取Cookie的值
RemeberUser.prototype.GetCookie = function(name) {
    var arg = name + "=";
    var alen = arg.length;
    var clen = document.cookie.length;
    var i = 0;
    while (i < clen) {
        var j = i + alen;
        if (document.cookie.substring(i, j) == arg)
            return this.getCookieVal(j);
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0)
            break;
    }
    return null;
};
// 获取cookie值
RemeberUser.prototype.getCookieVal = function(offset) {
    var endstr = document.cookie.indexOf(";", offset);
    if (endstr == -1)
        endstr = document.cookie.length;
    return unescape(document.cookie.substring(offset, endstr));
};

// 写入到Cookie
RemeberUser.prototype.SetCookie = function(name, value, expiresTime) {
    var argv = this.SetCookie.arguments;
    // 本例中length = 3
    var argc = this.SetCookie.arguments.length;
    var expires = (argc > 2) ? argv[2] : null;
    var path = (argc > 3) ? argv[3] : null;
    path="/";
    var domain = (argc > 4) ? argv[4] : null;

    var secure = (argc > 5) ? argv[5] : false;
    document.cookie = name + "=" + escape(value)
        + ((expires == null) ? "" : ("; expires=" + expires.toGMTString()))
        + ((path == null) ? "" : ("; path=" + path))
        + ((domain == null) ? "" : ("; domain=" + domain))
        + ((secure == true) ? "; secure" : "");
};
// 重置cookie
RemeberUser.prototype.ResetCookie = function() {
    var usr = $("#email").val();
    var expdate = new Date();
    this.SetCookie(usr, null, expdate);
};