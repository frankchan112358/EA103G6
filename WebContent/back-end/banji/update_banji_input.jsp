<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banji.model.*"%>

<%
	BanjiVO banjiVO = (BanjiVO) request.getAttribute("banjiVO");
%>

<jsp:useBean id="banjiTypeSvc" scope="page"
             class="com.banjitype.model.BanjiTypeService" />
<jsp:useBean id="empSvc" scope="page"
             class="com.emp.model.EmpService" />
<jsp:useBean id="classroomSvc" scope="page"
             class="com.classroom.model.ClassroomService" />
<jsp:useBean id="banjiSvc" scope="page"
             class="com.banji.model.BanjiService" />

<!DOCTYPE html>
<html>

<head>
    <%@ include file="/back-end/template/head.jsp" %>
    <style>
        .xdsoft_datetimepicker .xdsoft_datepicker {
            width: 300px;
            /* width:  300px; */
        }

        .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
            height: 151px;
            /* height:  151px; */
        }

        #add {
            text-align: center;
        }
    </style>
</head>

<body class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
    <script>
        var classHolder = document.getElementsByTagName("BODY")[0];
    </script>

    <div class="page-wrapper">
        <div class="page-inner">
            <%@ include file="/back-end/template/left_aside.jsp" %>
            <div class="page-content-wrapper">
                <%@ include file="/back-end/template/header.jsp" %>
                <main id="js-page-content" role="main" class="page-content">
                    <ol class="breadcrumb page-breadcrumb">
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/index/index.jsp">後台首頁</a></li>
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/banji/banji.manage">養成班管理</a></li>
                        <li class="breadcrumb-item"><a id="gotoReadBanji" href="javascript:void(0)">${banjiVO.banjiName}</a></li>
                        <li class="breadcrumb-item">班級設定</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-users-class'></i> 班級設定
                        </h1>
                    </div>
                    <div class="row">
                        <div class="col col-xl-12">
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                    <h2 class="text-white">總覽</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content">

                                        <form METHOD="post" ACTION="<%=request.getContextPath()%>/banji/banji.do" name="form1" class="was-validated">
                                            <div class="form-group">
                                                <label class="form-label" for="simpleinput">班種名稱:</label>
                                                <input type="text" id="simpleinput" class="form-control" readonly name="banjiTypeNo" value="${banjiVO.banjiTypeNo}">
                                            </div>

                                            <div class="form-group">
                                                <label class="form-label" for="example-date">開訓日:</label>
                                                <input class="form-control" name="startDay" id="f_date1" readonly onfocus="this.blur()">
                                            </div>

                                            <div class="form-group">
                                                <label class="form-label" for="example-date">結訓日:</label>
                                                <input class="form-control" name="endDay" id="f_date2" readonly onfocus="this.blur()">
                                            </div>

                                            <div class="form-group">
                                                <label class="form-label" for="simpleinput">班級名稱:</label>
                                                <input type="text" id="simpleinput" class="form-control" name="banjiName" readonly required value="${banjiVO.banjiName}">
                                                <div class="invalid-feedback">
                                                    班級名稱請勿空白且只能是中、英文字母、數字，其他不行。
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="form-label" for="simpleinput">上課時數:</label>
                                                <input type="text" id="simpleinput" class="form-control" name="classHours"  readonly required value="${banjiVO.classHours}">
                                                <div class="invalid-feedback">
                                                    請填寫上課時數.
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="form-label" for="simpleinput">學生人數:</label>
                                                <input type="text" id="simpleinput" class="form-control" name="numberOfStudent" required value="${banjiVO.numberOfStudent}">
                                                <div class="invalid-feedback">
                                                    請填寫上課人數.
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="form-label" for="simpleinput">教室:</label>
                                                <select class="custom-select form-control" name="classroomNo" required>
                                                    <option value="">請選擇教室</option>
                                                    <c:forEach var="classroomVO" items="${classroomSvc.all}">
                                                        <option value="${classroomVO.classroomNo}" ${(banjiVO.classroomNo==classroomVO.classroomNo)? 'selected' :'' }>${classroomVO.classroomName}
                                                    </c:forEach>
                                                </select>
                                                <div class="invalid-feedback">
                                                    教室請勿空白.
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="form-label" for="example-select">狀態:</label>
                                                <select class="custom-select form-control" name="status">
                                                    <option value="1" ${(banjiVO.status==1)?'selected':'' }>開課</option>
                                                    <option value="0" ${(banjiVO.status==0)?'selected':'' }>結訓</option>
                                                    <option value="2" ${(banjiVO.status==2)?'selected':'' }>延期</option>
                                                    <option value="3" ${(banjiVO.status==3)?'selected':'' }>未開課</option>
                                                </select>
                                                <div class="invalid-feedback">

                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="form-label" for="example-textarea">班級內容:</label>
                                                <textarea name="banjiContent" class="form-control" id="example-textarea" rows="5" placeholder="請輸入內容" required>${banjiVO.banjiContent }</textarea>
                                                <div class="invalid-feedback">
                                                    班級內容請勿空白.
                                                </div>
                                            </div>

                                            <div class="form-group" id="add">
                                                <input type="hidden" name="action" value="update">
                                                <input type="hidden" name="empNo" value="${empVO.empNo }">
                                                <input type="hidden" name="banjiNo" value="${banjiVO.banjiNo }">
                                                <button type="submit" class="btn btn-primary justify-content-center">送出修改</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
                <%@ include file="/back-end/template/footer.jsp" %>
            </div>
        </div>
    </div>
    <%@ include file="/back-end/template/quick_menu.jsp" %>
    <%@ include file="/back-end/template/messager.jsp" %>
    <%@ include file="/back-end/template/basic_js.jsp" %>

    <%
	java.sql.Date startDay = null;
	try {
		startDay = banjiVO.getStartDay();
	} catch (Exception e) {
		startDay = new java.sql.Date(System.currentTimeMillis());
	}

	java.sql.Date endDay = null;
	try {
		endDay = banjiVO.getEndDay();
	} catch (Exception e) {
		endDay = new java.sql.Date(System.currentTimeMillis());
	}
%>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.css" />
    <script src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.js"></script>
    <script
            src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.full.js"></script>



    <script>
        $(function () {
            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });

            $.datetimepicker.setLocale('zh');
            $('#f_date1').datetimepicker({
                theme: '',              //theme: 'dark',
                timepicker: false,       //timepicker:true,
                step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
                format: 'Y-m-d',         //format:'Y-m-d H:i:s',
                scrollInput: false,  //滾輪
                value: '<%=startDay%>',
                onShow: function () {
                    this.setOptions({
                        minDate: new Date()
                    })
                },
                timepicker: false,
            });

            $('#f_date2').datetimepicker({
                theme: '',              //theme: 'dark',
                timepicker: false,       //timepicker:true,
                step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
                scrollInput: false,  //滾輪
                format: 'Y-m-d',         //format:'Y-m-d H:i:s',
                value: '<%=endDay%>',
                onShow: function () {
                    this.setOptions({
                        minDate: $('#f_date1').val()
                    })
                },
                timepicker: false
            });

            $('#gotoReadBanji').click(function () {
                let _this = this;
                let myForm = banjiGotoFrom('<%=request.getContextPath()%>/banji/banji.manage');
                document.body.appendChild(myForm);
                myForm.append(banjiGotoInput('hidden', 'action', 'read'));
                myForm.submit();
            });

            function banjiGotoFrom(url) {
                let myForm = document.createElement('form');
                myForm.action = url;
                myForm.method = 'POST';
                return myForm;
            }

            function banjiGotoInput(type, name, value) {
                let banjiInput = document.createElement('input');
                banjiInput.type = type;
                banjiInput.name = name;
                banjiInput.value = value;
                return banjiInput;
            }
        });

    </script>
</body>

</html>