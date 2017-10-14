<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="A layout example that shows off a blog page with a list of posts.">
    <title>Blog &ndash; Layout Examples &ndash; Pure</title>

    <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-"
          crossorigin="anonymous">

    <!--[if lte IE 8]>
    <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/grids-responsive-old-ie-min.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/grids-responsive-min.css">
    <!--<![endif]-->


    <!--[if lte IE 8]>
    <link rel="stylesheet" href="/css/layouts/blog-old-ie.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="/css/layouts/blog.css">
    <!--<![endif]-->
</head>
<body>
<div id="layout" class="pure-g">
    <div class="sidebar pure-u-1 pure-u-md-1-4">
        <div class="header">
            <h1 class="brand-title">papao</h1>
            <h2 class="brand-tagline">유기동물 관리 플랫폼</h2>

            <nav class="nav">
                <ul class="nav-list">
                    <li class="nav-item">
                        <a class="pure-button" href="#">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="pure-button" href="#">Download</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>

    <div class="content pure-u-1 pure-u-md-3-4">
        <div>
            <!-- A wrapper for all the blog posts -->
        <#if post.id != -1>
            <div class="posts">
                <h1 class="content-subhead">Pinned Post #${post.id}</h1>

                <!-- A single blog post -->
                <section class="post">
                    <header class="post-header">
                        <img width="48" height="48" alt="bowwow" class="post-avatar"
                             src="${post.imageUrl}">

                        <h2 class="post-title">${post.kindName}</h2>

                        <p class="post-meta" style="color:black">
                            <#if post.type == '01'>
                                <a class="post-category post-category-design" href="#">외부 데이터</a>
                            <#elseif post.type == '02'>
                                <a class="post-category post-category-design" href="#">실종</a>
                            <#elseif post.type == '03'>
                                <a class="post-category post-category-design" href="#">보호</a>
                            </#if>
                            <#if post.state == 'PROCESS'>
                                <a class="post-category post-category-pure" href="#">진행중</a>
                            <#elseif post.state == 'RETURN'>
                                <a class="post-category post-category-pure" href="#">반환</a>
                            <#elseif post.state == 'NATURALDEATH'>
                                <a class="post-category post-category-pure" href="#">자연사</a>
                            <#elseif post.state == 'EUTHANASIA'>
                                <a class="post-category post-category-pure" href="#">안락사</a>
                            <#elseif post.state == 'ADOPTION'>
                                <a class="post-category post-category-pure" href="#">입양</a>
                            </#if>
                            <#if post.gender == 'M'>
                                <a class="post-category post-category-js" href="#">남아</a>
                            <#elseif post.gender == 'F'>
                                <a class="post-category post-category-js" href="#">여아</a>
                            <#else>
                                <a class="post-category post-category-js" href="#">성별미상</a>
                            </#if>
                            <#if post.neuter == 'Y'>
                                <a class="post-category post-category-yui" href="#">중성화 함</a>
                            <#else>
                                <a class="post-category post-category-yui" href="#">중성화 안함</a>
                            </#if>
                            <a class="post-category" href="#">${post.weight} kg</a>
                            <br/>
                            <br/>
                        ${post.happenDate} ${post.happenPlace} 에서 발견됨
                            <br/>
                            현재 <a href="#" class="post-author">${post.userName}</a> 님이 보호중
                            <br/>
                            연락처 : ${post.userContracts}
                        </p>
                    </header>

                    <div class="post-description">
                        <div class="post-images pure-g">
                            <div class="pure-u-1 pure-u-md-1-2">
                                <a href="#">
                                    <img alt="Photo of someone working poolside at a resort"
                                         class="pure-img-responsive"
                                         src="${post.imageUrl}">
                                </a>

                                <div class="post-image-meta">
                                    <h3>사진 1</h3>
                                </div>
                            </div>
                        </div>
                        <form class="pure-form pure-form-stacked">
                            <legend></legend>
                            <fieldset>
                                <div class="pure-g">
                                    <div class="pure-u-1 pure-u-md-1-3">
                                        <p>${post.feature}</p>
                                        <p>
                                        ${post.introduction}
                                        </p>
                                    </div>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </section>
            </div>
        <#else>
            검색 결과가 없습니다.
        </#if>
            <div class="footer">
                <div class="pure-menu pure-menu-horizontal">
                    <ul>
                        <li class="pure-menu-item"><a href="http://papaolabs.com/" class="pure-menu-link">About</a></li>
                        <li class="pure-menu-item"><a href="https://github.com/orgs/papaolabs"
                                                      class="pure-menu-link">GitHub</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>