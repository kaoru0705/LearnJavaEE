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
		let workList;
	
		// 이 함수는 상위, 하위를 모두 처리해야 하므로, 호출 시 상위를 원하는지, 하위를 원하는지 구분해줘야 한다.
		function printCategory(title, category, list){
			let tag = "<option value=''>"+title+"</option>";
			for(let i = 0; i < list.length; i++){
				if(category=="work.work_id"){
					tag += "<option value='"+list[i].work_id+"'>"+list[i].work_title+"("+list[i].running_time+"분)"+"</option>";
				}else if(category=="place.place_id"){
					tag += "<option value='"+list[i].place_id+"'>"+list[i].place_name+"</option>";
				}
			}
			$("select[name='"+category+"']").html(tag);
		}
		
		function registForm(){
		    let formData = new FormData(document.getElementById("form"));
		    
			$.ajax({
				url: "/admin/performance/round/regist",
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
		
		function getWork(){
			$.ajax({
				url:"/admin/performance/work/list",
				method:"GET",
				
				success:function(result, status, xhr){
					// select2는 placeholder를 쓴다. title은 ""
					printCategory("", "work.work_id", result);
					
					workList = result;
					
				    // Select2 초기화
				    $("select[name='work.work_id']").select2({
				        theme: 'bootstrap4',
				        placeholder: "작품 검색",
				        allowClear: true,
				        width: '100%'	// 이걸 넣지 않으면 크기가 유동적이지 않음
				    });
				    
					console.log(result);
				},
				error:function(xhr, status, err){
					
				}
			});
		}
		
		function getPlace(){
			$.ajax({
				url:"/admin/performance/place/list",
				method:"GET",
				
				success:function(result, status, xhr){
					// select2는 placeholder를 쓴다. title은 ""
					printCategory("", "place.place_id", result);
					
				    // Select2 초기화
				    $("select[name='place.place_id']").select2({
				        theme: 'bootstrap4',
				        placeholder: "장소 검색",
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

			getWork();
			getPlace();
			
			
			$("#regist").click(()=>{
				registForm();
			});
			
			$("select[name='work.work_id']").change();
			
			
			
			// 한국어 로케일 설정 (moment.js가 로드되어 있어야 함)
		    moment.locale('ko');

/* 		    $("#ticket_start_date").datetimepicker({
		        icons: { time: 'far fa-clock' },
		        format: 'YYYY-MM-DD HH:mm',
		        locale: 'ko',
		        dayViewHeaderFormat: 'YYYY년 MMMM',	// 년 월 순서로
		        ignoreReadonly: true
		    });
			
		 	// 시작일 초기화
		    $("#work_start_date").datetimepicker({
		        format: 'YYYY-MM-DD',
		        locale: 'ko',
		        dayViewHeaderFormat: 'YYYY년 MMMM'
		    });

		    // 종료일 초기화
		    $("#work_end_date").datetimepicker({
		        format: 'YYYY-MM-DD',
		        locale: 'ko',
		        dayViewHeaderFormat: 'YYYY년 MMMM',
		        useCurrent: false // 시작일 선택 전까지 자동 선택 방지
		    });

		    $("#ticket_start_date").on("change.datetimepicker", function (e) {
		        $("#work_start_date").datetimepicker('minDate', e.date);
		    });

		    // 시작일이 바뀌면 종료일의 선택 가능 범위를 제한
		    $("#work_start_date").on("change.datetimepicker", function (e) {
		        $("#work_end_date").datetimepicker('minDate', e.date);
		        $("#ticket_start_date").datetimepicker('maxDate', e.date);
		    });

		    // 종료일이 바뀌면 시작일의 선택 가능 범위를 제한
		    $("#work_end_date").on("change.datetimepicker", function (e) {
		        $("#work_start_date").datetimepicker('maxDate', e.date);
		    }); */
		})
	</script>
	<div class="container-fluid mt-5">
		<div class="row justify-content-center">
			<div class="col-md-8">
	            <div class="card card-info">
	              <div class="card-header">
	                <h3 class="card-title">동일 날짜 회차 다중 등록</h3>
	              </div>
					<form id="form">
						<div class="card-body">
							<div class="form-group row">
								<div class="col-md-6">
									<select class="form-control select2 select2-info" name="work.work_id"></select>
							    </div>
								<div class="col-md-6">
									<select class="form-control select2 select2-info" name="place.place_id"></select>
							    </div>	
							</div>
							<div class="form-group">
								<label>공연 날짜:</label>
								<div class="input-group date" id="round_date" data-target-input="nearest">
									<input type="text" class="form-control datetimepicker-input" data-target="#round_date" name=round_date>
									<div class="input-group-append" data-target="#round_date" data-toggle="datetimepicker">
										<div class="input-group-text"><i class="fa fa-calendar"></i></div>
									</div>
								</div>
							</div>
							<div class="form-group row">
								<div class="col-md-11">
							        <label>회차 시작 시간</label>
							        <div class="input-group date" id="round_start_time" data-target-input="nearest">
							            <input type="text" class="form-control datetimepicker-input" data-target="#round_start_time" name="round_start_time" />
							            <div class="input-group-append" data-target="#round_start_time" data-toggle="datetimepicker">
							                <div class="input-group-text"><i class="far fa-clock"></i></div>
							            </div>
								    </div>
								</div>
								<div class="col-md-1">
									<button type="button" class = "btn btn-outline-danger remove">X</button>
								</div>
							</div>
						</div>
						<div class="card-footer text-center">
							<button type="button" id="append" class="btn btn-outline-info">추가하기</button>
							<button type="button" id="regist" class="btn btn-success">등록</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>