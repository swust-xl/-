function check_uname(){
	var name = document.getElementById("username").value;
	var show = document.getElementById("show_box");
	if(name.length>7||name.length<2)
	{
		show.innerHTML = "用户名长度须在2-7之间";
		return false;
	}
	for(var i=0 ;i < name.length ; i++)
	{
		var text=name.charAt(i);
		if(!(text<=9&&text>=0)&&!(text>='a'&&text<='z')&&!(text>='A'&&text<='Z')&&text!="_")
		{
			show_box.innerHTML = "用户名只能是数字、字母、下划线组成！";
			break;
		}
	}
	if(i>=name.length)
	{
		show.innerHTML = "正确";
		return true;
	}
}

function check_pass(){
	var password = document.getElementById("password").value;
	var show = document.getElementById("show_box1");
	if(password.length>12||password.length<6)
	{
		show.innerHTML = "密码长度须在6-12之间";
		return false;
	}
	show.innerHTML = "正确";
	return true;
}

function check_email(){
	var email = document.getElementById("email");
	var show = document.getElementById("show_box2");
	var myreg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	if(!myreg.test(email.value))
	{
		show.innerHTML = "邮箱格式错误";
		return false;
	}
	show.innerHTML = "正确";
	return true;
}

function choose(){
var obj = document.getElementsByTagName("input");
    for(var i=3; i<obj.length; i++){
        if(obj[i].checked){
			return obj[i].value;
            break;
        }
    }
}