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
	
		// 이 함수는 상위, 하위를 모두 처리해야 하므로, 호출 시 상위를 원하는지, 하위를 원하는지 구분해줘야 한다.
		function printCategory(title, category, list){
			let tag = "<option value='0'>"+title+"</option>";
			for(let i = 0; i < list.length; i++){
				if(category=="genre"){
					tag += "<option value='"+list[i].genre_id+"'>"+list[i].genre_name+"</option>"	// 기존 <option> 태그에 누적
				}else if(category=="publisher"){
					tag += "<option value='"+list[i].publisher_id+"'>"+list[i].publisher_name+"</option>"	// 기존 <option> 태그에 누적
				}
			}
			$("select[name='"+category+"']").html(tag);
		}
		
		function registForm(){
		    let formData = new FormData();
			
		    /* https://www.w3schools.com/jquery/traversing_each.asp JQuery transvering*/
		    $(".form-group").each(function (){
		        const name = $(this).find("input[name='person_name']").val();
		        const fileInput = $(this).find("input[type='file']")[0];
		        /*https://www.w3schools.com/jsref/dom_obj_fileupload.asp*/
		        const file = fileInput.files[0];
	
		        if(!name || !file){
		        	return;
		        }
				
		        formData.append("person_name", name);
	
		        formData.append("profile_img", file);
		    });
			
			console.log(formData);
			$.ajax({
				url: "/admin/performance/work/regist",
				method: "POST",
				data: formData,
				processData: false,
				contentType: false,
				success:function(result, status, xhr){
					alert(result.message); 
				},
				error:function(xhr, status, err){
					let obj = JSON.parse(xhr.responseText);
					alert(obj.message);
				}
			})		    
		}
		function previewImage(file, row) {
		    const reader = new FileReader();
		    
			// 기존 이미지 있으면 제거 자손 중에서 찾는 method인 find()
	        row.find("img").remove();
			
	        const previewContainer = row.find(".preview-box");
	        if(!file) return;
	        
		    reader.onload = function (e) {
	
		        const img = $("<img>")
		            .attr("src", e.target.result)
		            .css({
		                width: "60px",
		                height: "60px",
		            });
	
		        previewContainer.append(img);
		    };
	
		    reader.readAsDataURL(file);
		}
		
		function getGenre(){
			$.ajax({
				url:"/admin/performance/genre/list",
				method:"GET",
				
				success:function(result, status, xhr){
					printCategory("장르 선택", "genre", result);
					console.log(result);
				},
				error:function(xhr, status, err){
					
				}
			});
		}
		
		function getPublisher(){
			$.ajax({
				url:"/admin/performance/publisher/list",
				method:"GET",
				
				success:function(result, status, xhr){
					printCategory("주최/기획 선택", "publisher", result);
					
				    // Select2 초기화
				    $('.select2').select2({
				        theme: 'bootstrap4',
				        placeholder: "검색하여 선택하세요",
				        allowClear: true,
				        width: '100%'	// 이걸 넣지 않으면 크기가 유동적이지 않음
				    });
				    
					console.log(result);
				},
				error:function(xhr, status, err){
					
				}
			});
		}		

		$(()=>{
			/* https://www.w3schools.com/jquery/event_on.asp */
			$(".card-body").on("change", "input[type=file]", function(e){
				//console.log("this ", this);
				//console.log("this.files", this.files);
				//console.log("e.target.files", e.target.files);
				
				let file = this.files[0];
				
				// 가장 가까운 위치의 dom 선택
				previewImage(file, $(this).closest(".form-group"));
			});
		
			$(function () {

			});
			
			getGenre();
			getPublisher();
			
			
			$("#regist").click(()=>{
				registForm();
			});
		})
	</script>
	<div class="container-fluid mt-5">
		<div class="row justify-content-center">
			<div class="col-md-8">
	            <div class="card card-info">
	              <div class="card-header">
	                <h3 class="card-title">작품 등록</h3>
	              </div>
					<form id="form">
						<div class="card-body">
							<div class="form-group row">
								<div class="col-md-4">
									<input type="text" class="form-control" name="work_title"  placeholder="제목 입력">
								</div>
								<div class="col-md-2">
									<input type="text" class="form-control" name="director"  placeholder="감독">
								</div>
								<div class="col-md-2">
									<input type="number" class="form-control" name="age_limit"  placeholder="연령제한">
								</div>
								<div class="col-md-2">
									<input type="number" class="form-control" name="work_price"  placeholder="가격">
								</div>
								<div class="col-md-2">
									<input type="number" class="form-control" name="running_time"  placeholder="러닝타임(단위:분)">
								</div>
							</div>
						
							<div class="form-group row">
								<div class="col-md-4">
									<select class="form-control" name="genre"></select>
							    </div>	
								<div class="col-md-6">
									<select class="form-control select2 select2-info" name="publisher"></select>
							    </div>
								<div class="col-md-2">
									<select class="form-control" name="work_type">
										<option value="round">회차</option>
										<option value="period">상시</option>
									</select>
							    </div>
							</div>
							<div class="form-group row align-items-center">
								<div class="col-md-10">
									<div class="custom-file">
										<input type="file" class="custom-file-input" name="work_poster_img">
										<label class="custom-file-label">작품 포스터 선택</label>
									</div>
								</div>
								<div class="col-md-2 text-center preview-box">
				                </div>
							</div>
							<div class="form-group row align-items-center">
								<div class="col-md-10">
									<div class="custom-file">
										<input type="file" class="custom-file-input" name="work_content_img">
										<label class="custom-file-label">작품 내용 선택</label>
									</div>
								</div>
								<div class="col-md-2 text-center preview-box">
				                </div>
							</div>
							<div class="form-group row">
								<div class="col-md-4">
                  				</div>
								<div class="col-md-4">
                  				</div>
								<div class="col-md-4">
                  				</div>
							</div>
						</div>
						<div class="card-footer text-center">
							<button type="button" id="regist" class="btn btn-success">등록</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>