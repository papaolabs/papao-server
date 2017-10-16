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

    <style>
        .mdl-card__media {
            background-color: #f5f5f5 !important;
        }

        .thumbnail-wrappper {
            width: 25%;
        }

        .thumbnail {
            position: relative;
            padding-top: 100%; /* 1:1 ratio */
            overflow: hidden;
        }

        .thumbnail .centered {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            -webkit-transform: translate(50%, 50%);
            -ms-transform: translate(50%, 50%);
            transform: translate(50%, 50%);
        }

        .thumbnail .centered img {
            position: absolute;
            top: 0;
            left: 0;
            max-width: 100%;
            height: auto;
            -webkit-transform: translate(-50%, -50%);
            -ms-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
        }
    </style>
</head>

<body>
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <header class="mdl-layout__header mdl-layout__header--waterfall portfolio-header">
        <div class="mdl-layout__header-row portfolio-logo-row">
                <span class="mdl-layout__title">
                    <div class="portfolio-logo"></div>
                </span>
        </div>
        <div class="mdl-layout__header-row portfolio-navigation-row mdl-layout--large-screen-only">
            <nav class="mdl-navigation mdl-typography--body-1-force-preferred-font">
                <a class="mdl-navigation__link"
                   href="/dashboard">Home</a>
                <a class="mdl-navigation__link"
                   href="#">About</a>
                <a class="mdl-navigation__link"
                   href="#">Contact</a>
            </nav>
        </div>
    </header>
    <div class="mdl-layout__drawer mdl-layout--small-screen-only">
        <nav class="mdl-navigation mdl-typography--body-1-force-preferred-font">
            <a class="mdl-navigation__link is-active"
               href="/dashboard">Home</a>
            <a class="mdl-navigation__link"
               href="#">About</a>
            <a class="mdl-navigation__link"
               href="#">Contact</a>
        </nav>
    </div>
    <main class="mdl-layout__content">
        <div class="mdl-grid portfolio-max-width">
            <div class="mdl-grid mdl-cell mdl-cell--12-col mdl-cell--8-col-tablet mdl-card mdl-shadow--8dp">
                <div class="mdl-card__media mdl-cell mdl-cell--12-col-tablet">
                    <div class="thumbnail-wrapper">
                        <div class="thumbnail">
                            <div class="centered">
                                <img class="article-image" src="${post.imageUrl}">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mdl-cell mdl-cell--8-col">
                    <h2 class="mdl-card__title-text">${post.kindName}</h2>
                    <div class="mdl-card__supporting-text padding-top">
                        <div style="margin-bottom:15px;">
                        <#if post.type == '01'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">기관</span>
                            </span>
                        <#elseif post.type == '02'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">실종</span>
                            </span>
                        <#elseif post.type == '03'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">보호</span>
                            </span>
                        </#if>
                        <#if post.state == 'PROCESS'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">진행중</span>
                            </span>
                        <#elseif post.state == 'RETURN'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">반환</span>
                            </span>
                        <#elseif post.state == 'NATURALDEATH'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">자연사</span>
                            </span>
                        <#elseif post.state == 'EUTHANASIA'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">안락사</span>
                            </span>
                        <#elseif post.state == 'ADOPTION'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">입양</span>
                            </span>
                        </#if>
                        <#if post.gender == 'M'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">남아</span>
                            </span>
                        <#elseif post.gender == 'F'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">여아</span>
                            </span>
                        <#else>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">성별미상</span>
                            </span>
                        </#if>
                        <#if post.neuter == 'Y'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">중성화 함</span>
                            </span>
                        <#else>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">중성화 안함</span>
                            </span>
                        </#if>
                            <span class="mdl-chip">
                            <span class="mdl-chip__text">${post.weight} kg</span>
                        </span>
                        </div>
                        <div id="tt1" class=" icon material-icons portfolio-share-btn">share</div>
                        <div class="mdl-tooltip" for="tt1">
                            Share via social media
                        </div>
                    </div>
                    <div class="mdl-card__supporting-text no-left-padding">
                        <p>
                        ${post.happenDate} ${post.happenPlace} 에서 발견됨<br/>
                            현재 <a href="#" class="post-author">${post.userName}</a> 님이 보호중<br/>
                            연락처 : ${post.userContracts}<br/>
                        ${post.feature}<br/><br/>
                        ${post.introduction}
                        </p>
                    </div>
                </div>
                <div class="mdl-card__actions mdl-card--border">
                <#--<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect mdl-button--accent">
                    View Updates
                </a>-->
                    <ul class="demo-list-two mdl-list">
                        <li class="mdl-list__item mdl-list__item--two-line" style="border-bottom:1px solid #f5f5f5;">
    <span class="mdl-list__item-primary-content">
      <i class="material-icons mdl-list__item-avatar">person</i>
      <span>User 1</span>
      <span class="mdl-list__item-sub-title">body</span>
    </span>
    <span class="mdl-list__item-secondary-content">
      <a class="mdl-list__item-secondary-action" href="#"><i class="material-icons">star</i></a>
    </span>
                        </li>
                        <li class="mdl-list__item mdl-list__item--two-line" style="border-bottom:1px solid #f5f5f5;">
    <span class="mdl-list__item-primary-content">
      <i class="material-icons mdl-list__item-avatar">person</i>
      <span>User 2</span>
      <span class="mdl-list__item-sub-title">body</span>
    </span>
    <span class="mdl-list__item-secondary-content">
      <a class="mdl-list__item-secondary-action" href="#"><i class="material-icons">star</i></a>
    </span>
                        </li>
                        <li class="mdl-list__item mdl-list__item--two-line" style="border-bottom:1px solid #f5f5f5;">
    <span class="mdl-list__item-primary-content">
      <i class="material-icons mdl-list__item-avatar">person</i>
      <span>User 3</span>
      <span class="mdl-list__item-sub-title">body</span>
    </span>
    <span class="mdl-list__item-secondary-content">
      <a class="mdl-list__item-secondary-action" href="#"><i class="material-icons">star</i></a>
    </span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <footer class="mdl-mini-footer">
            <div class="mdl-mini-footer__left-section">
                <div class="mdl-logo">papao</div>
            </div>
            <div class="mdl-mini-footer__right-section">
                <ul class="mdl-mini-footer__link-list">
                    <li><a href="#">Help</a></li>
                    <li><a href="#">Privacy & Terms</a></li>
                </ul>
            </div>
        </footer>
    </main>
</div>
<script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
</body>

</html>
