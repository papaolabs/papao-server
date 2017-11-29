<!doctype html>
<!--
  Material Design Lite
  Copyright 2015 Google Inc. All rights reserved.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License
-->
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="A portfolio template that uses Material Design Lite.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>papao</title>
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.grey-pink.min.css"/>
    <link rel="stylesheet" href="/css/styles.css"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>

<body>
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <header class="demo-header mdl-layout__header mdl-layout__header--scroll mdl-color--grey-100 mdl-color-text--grey-800">
        <div class="mdl-layout__header-row" style="padding-left:20px !important;">
            <span class="mdl-layout-title" style="cursor:default">Milou</span>
            <div class="mdl-layout-spacer"></div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable" style="margin-bottom:25px;">
                <a href="#" class="mdl-button mdl-js-button mdl-button--icon">
                    <div id="tt1" class=" icon material-icons">phone_iphone</div>
                    <div class="mdl-tooltip" for="tt1">
                        앱에서 보기
                    </div>
                </a>
            </div>
        </div>
    </header>
    <main class="mdl-layout__content">
        <div class="mdl-grid portfolio-max-width">
            <div class="mdl-grid mdl-cell mdl-cell--12-col mdl-cell--8-col-tablet mdl-card mdl-shadow--8dp">
                <div class="mdl-card__media mdl-cell mdl-cell--12-col-tablet">
                    <div class="thumbnail-wrapper">
                        <div class="thumbnail">
                            <div class="centered">
                                <img class="article-image" src="${post.imageUrls[0].url}">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mdl-cell mdl-cell--8-col">
                    <h2 class="mdl-card__title-text">${post.kindName}<label
                            style="font-size:18px;color:grey;margin-top:2px;margin-left:3px;"><#if post.genderType == 'M'>
                        (수컷)
                    <#elseif post.genderType == 'F'>
                        (암컷)
                    <#else>
                        (성별미상)
                    </#if></label></h2>
                    <div class="mdl-card__supporting-text padding-top">
                        <div style="margin-bottom:15px;">
                        <#if post.postType == 'SYSTEM'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">보호소</span>
                            </span>
                        <#elseif post.postType == 'PROTECTING'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">임시보호</span>
                            </span>
                        <#elseif post.postType == 'ROADREPORT'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">길거리제보</span>
                            </span>
                        <#elseif post.postType == 'MISSING'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">실종</span>
                            </span>
                        <#elseif post.postType == 'UNKNOWN'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">상태미상</span>
                            </span>
                        </#if>
                        <#if post.stateType == 'PROCESS'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">진행중</span>
                            </span>
                        <#elseif post.stateType == 'RETURN'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">반환</span>
                            </span>
                        <#elseif post.stateType == 'NATURALDEATH'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">자연사</span>
                            </span>
                        <#elseif post.stateType == 'EUTHANASIA'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">안락사</span>
                            </span>
                        <#elseif post.stateType == 'ADOPTION'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">입양</span>
                            </span>
                        <#elseif post.stateType == 'UNKNOWN'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">상태미상</span>
                            </span>
                        </#if>
                        <#if post.neuterType == 'Y'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">중성화 함</span>
                            </span>
                        <#elseif post.neuterType == 'N'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">중성화 안함</span>
                            </span>
                        <#else>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">알수없음</span>
                            </span>
                        </#if>
                            <span class="mdl-chip">
                            <span class="mdl-chip__text">${post.weight} kg</span>
                        </span>
                        </div>
                    </div>
                    <div class="mdl-card__supporting-text no-left-padding">
                        <p>
                            <a href="tel:${post.managerContact}" class="post-author">${post.managerName}</a>
                            님이 ${post.noticeBeginDate}에
                            올린 정보입니다<br/>
                            <br/>발견 장소 : ${post.happenPlace}
                            <br/>발견 일자 : ${post.happenDate}
                            <br/>연락처 : ${post.managerContact}
                            <br/>특징 : ${post.feature}
                        </p>
                    </div>
                </div>
                <div class="mdl-card__actions mdl-card--border">
                    <ul class="demo-list-two mdl-list">
                    <#list comments.contents as comment>
                        <li class="mdl-list__item mdl-list__item--two-line"
                            style="border-bottom:1px solid #f5f5f5;">
                        <span class="mdl-list__item-primary-content">
                          <img class="mdl-list__item-avatar" src="${comment.profileUrl}"
                               style="height:40px;width:40px;">
                          <span>${comment.nickname}</span>
                          <span class="mdl-list__item-sub-title">${comment.text}</span>
                        </span>
                        </li>
                    </#list>
                    </ul>
                </div>
            </div>
        </div>
        <footer class="demo-footer mdl-mini-footer">
            <div class="mdl-mini-footer--left-section">
                <ul class="mdl-mini-footer--link-list">
                    <li><a href="#">Home</a></li>
                </ul>
            </div>
        </footer>
    </main>
</div>
<script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
</body>
</html>
