$(document).ready(function(){
	SetBodyHeight();
});

//top navigate event
//function TopNav(){
//	//bind navigate event 
//	$(".nav-underline li a").on("click", function(){
//		$(".nav-underline").find(".myActive").removeClass("myActive");
//		$(this).parent().addClass("myActive");
//	});
//	setTopNav();
//}
//function setTopNav(){
//	//show underline
//	var urlStr = window.location.href;
//	if (urlStr.match("readingCenter")) {
//		$(".nav-underline li").eq(1).addClass("myActive");
//	}
//	else if (urlStr.match("readingSea")) {
//		$(".nav-underline li").eq(2).addClass("myActive");
//	}
//	else if (urlStr.match("readingTraining")) {
//		$(".nav-underline li").eq(3).addClass("myActive");
//	}
//	else if (urlStr.match("prizeCenter")) {
//		$(".nav-underline li").eq(4).addClass("myActive");
//	}
//	else{
//		$(".nav-underline li").eq(0).addClass("myActive");
//	}
//}

//left navigate event in page reading center
function ReadingCenterLeftNav(){
	$(".nav-sidebar li").on("click", function(){
		$(".nav-sidebar li").removeClass("myActive");
		$(this).addClass("myActive");
	});

	var urlStr = window.location.href;
	if (urlStr.match("readingCenter/myNote")) {
		$(".nav-sidebar li").eq(1).addClass("myActive");
	}
	else if (urlStr.match("readingCenter/myEvaluating")) {
		$(".nav-sidebar li").eq(2).addClass("myActive");
	}
	else if (urlStr.match("readingCenter/myEvaluate")) {
		$(".nav-sidebar li").eq(3).addClass("myActive");
	}
	else{
		$(".nav-sidebar li").eq(0).addClass("myActive");
	}
}

function SetReadingCenterRightHeight(){
	$(".readingCenter-right").css('min-height' ,document.documentElement.clientHeight - 80);
	$(window).resize(function(){
		$(".readingCenter-right").css('min-height' ,document.documentElement.clientHeight - 80);
	});
}

function SetBodyHeight(){
	$(".myBody").css('min-height' ,document.documentElement.clientHeight - 136);
	$(window).resize(function(){
		$(".myBody").css('min-height' ,document.documentElement.clientHeight - 136);
	});
}

//reading center -> my bookshelf -> book list
function SetBookList(){
	$(".select-tab").on("click", function(){
		$(".select-tab.myActive").removeClass("myActive");
		$(this).addClass("myActive");
	});

	var urlStr = window.location.href;
	if (urlStr.match("readingCenter/addBook/advanced")) {
		$(".readingCenter-right-body .find .select-tab").eq(1).addClass("myActive");
	}
	else if (urlStr.match("readingCenter/addBook/popular")) {
		$(".readingCenter-right-body .find .select-tab").eq(2).addClass("myActive");
	}
	else if (urlStr.match("readingCenter/addBook/recommend")) {
		$(".readingCenter-right-body .find .select-tab").eq(3).addClass("myActive");
	}
	else{
		$(".readingCenter-right-body .find .select-tab").eq(0).addClass("myActive");
	}
}


function SetBookDetailList(){
	$(".select-tab").on("click", function(){
		$(".select-tab.myActive").removeClass("myActive");
		$(this).addClass("myActive");
	});

	var urlStr = window.location.href;
	if (urlStr.match("readingCenter/bookDetails/bookOutline")) {
		$(".readingCenter-right-body .bookDetailBottom .select-tab").eq(1).addClass("myActive");
	}
	else{
		$(".readingCenter-right-body .bookDetailBottom .select-tab").eq(0).addClass("myActive");
	}
}

function SetOption(){
	$(".option").removeClass("active");
	$(".option").on("click", function(){
		$(".option.active").removeClass("active");
		$(this).addClass("active");
	});
}


function ReadingTrainingLeftNav(){
    $(".nav-sidebar li").on("click", function(){
        $(".nav-sidebar li").removeClass("myActive");
        $(this).addClass("myActive");
    });

    var urlStr = window.location.href;
    if (urlStr.match("readingTraining/training")) {
        $(".nav-sidebar li").eq(1).addClass("myActive");
    }
    else if (urlStr.match("readingTraining/stat")) {
        $(".nav-sidebar li").eq(2).addClass("myActive");
    }
    else{
        $(".nav-sidebar li").eq(0).addClass("myActive");
    }
}
