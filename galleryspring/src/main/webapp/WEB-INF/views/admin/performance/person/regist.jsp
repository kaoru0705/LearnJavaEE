<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script>
		function registForm(){
		    let formData = new FormData();
			
		    /* https://www.w3schools.com/jquery/traversing_each.asp JQuery transvering*/
		    $(".input_row").each(function (){
		        const name = $(this).find("input[name='person_name']").val();
		        const fileInput = $(this).find("input[type='file']")[0];
		        /*https://www.w3schools.com/jsref/dom_obj_fileupload.asp*/
		        const file = fileInput.files[0];
	
		        if (!name) return;
		        if (!file) return;
				
		        formData.append("person_name", name);
	
		        formData.append("profile_img", file);
		    });
			console.log(formData);
			$.ajax({
				url: "/admin/performance/person/regist",
				method: "POST",
				data: formData,
				processData: false,
				contentType: false,
				success:function(result, status, xhr){
					if(result === "success"){
						alert("인물 등록 성공");
					} else{
						alert("인물 등록 실패");
					}
				},
				error:function(xhr, status, err){
					
				}
			})		    
		}
		function previewImage(file, row) {
		    const reader = new FileReader();
		    
	        row.find("img").remove();
	        if(!file) return;
	        
		    reader.onload = function (e) {
		        // 기존 이미지 있으면 제거 자손 중에서 찾는 method인 find()
	
		        const img = $("<img>")
		            .attr("src", e.target.result)
		            .css({
		                width: "80px",
		                height: "80px",
		                display: "block",
		                margin: "auto"
		            });
	
		        row.append(img);
		    };
	
		    reader.readAsDataURL(file);
		}
		function add(){
			let row = `
				<div class="input_row">
					<input type="text" name="person_name" placeholder="이름 입력">
					<input type="file" name="profile_img" placeholder="프로필 입력">
					<button type="button" class = "remove">X</button>
				</div>
			`;
			$("#input_area").append(row);
		}
	
		$(()=>{
			$("#append").click(()=>{
				add();
			})
			/* https://www.w3schools.com/jquery/event_on.asp */
			$("#input_area").on("change", "input[type=file]", function(e){
				//console.log("this ", this);
				//console.log("this.files", this.files);
				//console.log("e.target.files", e.target.files);
				
				let file = this.files[0];
				

				// 가장 가까운 위치의 dom 선택
				previewImage(file, $(this).closest(".input_row"));
			})
			
			$("#input_area").on("click", ".remove", function(){
				$(this).closest(".input_row").remove();
			})
			
			$("#regist").click(()=>{
				registForm();
			});
		})
	</script>
	<div class="content" style="text-align: center">	
		<form id="form">
			<div id="input_area">
			</div>
			<button type="button" id="append">추가하기</button>
			<div>
				<button type="button" id="regist">등록</button>
			</div>
		</form>
	</div>
</body>
</html>