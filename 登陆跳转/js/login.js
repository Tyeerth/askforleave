function userlogin(){
	var data=$("#userlogin").serialize();
	var name = document.getElementById('username').value;
	if(name == 'xtmxzzb'){
		$.post("askForLeave/Login?opt=login",data,function(data,status){
		if(status == 'success'){
			window.location.href = 'askForLeave/user_information.html';
		}
	});
	}else if(name == 'xtmxrsj'){
		$.post("askForLeave3/Login?opt=login",data,function(data,status){
		if(status == 'success'){
			window.location.href = 'askForLeave3/user_information.html';
		}
	});
	}
	
}