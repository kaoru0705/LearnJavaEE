let memberInfo = {
	email: "이메일",
	password: "비밀번호",
	nickname: "닉네임",
	phone: "휴대폰번호"
}

let memberIsValid = {
	email: false,
	password: false,
	nickname: false,
	phone: true
}

function setSignupButton(set){
	let button = document.querySelector(".buttonSignup");
	if(set) {
		button.disabled = false;
		button.style.backgroundColor = "#04AA6D";
		button.style.color = "white";
	}else {
		button.disabled = true;
		button.style.backgroundColor = "#f1f1f1";
		button.style.color = "gray";
	}
}

// member 중복성 검사
async function validateMember(check){
	let checkId = document.getElementById(check);
	let memberTag = document.getElementById(check + "Msg");

	if(checkId.value == "" && check != "phone") {
		memberTag.innerHTML = "&middot; [필수] " + memberInfo[check];
		memberTag.style.display = "block";
		checkId.style.border = "1px solid crimson";
		memberIsValid[check] = false;
		setSignupButton(false);
		return;
	}
	if(check != "password") {
		//let checkSite = "http://localhost:8888/member/signup/check.jsp?" + check + "=" + checkId.value;
		
		//let res = await fetch(checkSite);
		//let data = await res.text();
		let checkSite = "http://localhost:8888/member/signup/check.jsp";
		let res = await fetch(checkSite, {
			method: "POST",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded"
			},
			body: `${check}=${checkId.value}`
		});
		let data = await res.text();
		console.log(data);
		/* 기본으로 공백이 들어가 있어 지워야 한다.*/
		let trimmedData = data.trim();
		let postposition = (check === "phone") ? "를" : "을";
		if(trimmedData === "invalid") {
			memberTag.innerHTML = "&middot; 사용할 수 없는 " + memberInfo[check] + "입니다. 다른 " + memberInfo[check] + postposition + " 입력해주세요.";
			memberTag.style.display = "block";
			checkId.style.border = "1px solid crimson";
			memberIsValid[check] = false;
			setSignupButton(false);
			return;
		}
	}
	
	memberTag.style.display = "none";
	checkId.style.border = "none";	
	memberIsValid[check] = true;
	
	let isValid = true;
	for(let value of Object.values(memberIsValid)) {
		if(!value) {
			isValid = false;
			return;
		}
	}
	
	setSignupButton(isValid);
}