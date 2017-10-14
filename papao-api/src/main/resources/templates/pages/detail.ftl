<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
    <div class="container">
        <h1 class="display-3">Papao</h1>
        <p>유기동물 관리 플랫폼</p>
    </div>
</div>

<div class="container">
    <!-- Example row of columns -->
    <div class="row">
        <div class="col-md-12">
            <img src="${post.imageUrl}" style="width:340px;height:250px">
            <p>게시번호 : ${post.id}</p>
            <p>게시타입 : ${post.type}</p>
            <p>축종 : ${post.kindUpCode}</p>1
            <p>품종 : ${post.kindCode}</p>
            <p>발생일 : ${post.happenDate}</p>
            <p>발생장소 : ${post.happenPlace}</p>
            <p>무게 : ${post.weight}</p>
            <p>성별 : ${post.gender}</p>
            <p>상태 : ${post.state}</p>
            <p>중성화 : ${post.neuter}</p>
            <p>특징 : ${post.feature}</p>
            <p>소개 : ${post.introduction}</p>
            <p><a class="btn btn-primary btn-lg" href="#" role="button">앱에서 보기 &raquo;</a></p>        </div>
    </div>

    <hr>

    <footer>
        <p>&copy; Company 2017</p>
    </footer>
</div> <!-- /container -->