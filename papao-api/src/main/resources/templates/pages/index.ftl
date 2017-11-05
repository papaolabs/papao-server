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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/dialog-polyfill/0.4.9/dialog-polyfill.min.css" />
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.grey-pink.min.css"/>
    <link rel="stylesheet" href="/css/styles.css"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>

<body>
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <header class="demo-header mdl-layout__header mdl-layout__header--scroll mdl-color--grey-100 mdl-color-text--grey-800">
        <div class="mdl-layout__header-row">
            <span class="mdl-layout-title">papao</span>
            <div class="mdl-layout-spacer"></div>
        </div>
    </header>
    <div class="mdl-layout__drawer">
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
        <#list posts as post>
            <div class="mdl-cell mdl-card mdl-shadow--4dp portfolio-card">
                <div class="mdl-card__media">
                    <div class="thumbnail-wrapper">
                        <div class="thumbnail">
                            <div class="centered">
                                <img class="article-image lazyload" data-src="${post.imageUrl}">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mdl-card__title">
                    <h2 class="mdl-card__title-text">${post.kindName}</h2>
                </div>
                <div class="mdl-card__supporting-text">
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
                                <span class="mdl-chip__text">수컷</span>
                            </span>
                        <#elseif post.gender == 'F'>
                            <span class="mdl-chip">
                                <span class="mdl-chip__text">암컷</span>
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
                    <div>
                    ${post.happenDate} ${post.happenPlace} 에서 발견됨<br/>
                        현재 <a href="#" class="post-author">${post.userName}</a> 님이 보호중<br/>
                        연락처 : ${post.userContact}<br/>
                    ${post.feature}<br/><br/>
                    ${post.introduction}
                    </div>
                </div>
                <div class="mdl-card__actions mdl-card--border">
                    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect mdl-button--accent"
                       href="/dashboard/detail?pageId=${post.id?c}">Read more</a>
                </div>
            </div>
        </#list>
        </div>
        <dialog class="mdl-dialog" id="modal-example">
            <div class="mdl-dialog__content">
                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label getmdl-select getmdl-select__fullwidth">
                    <label for="sample" class="mdl-textfield__label">검색기간</label>
                    <input id="search-period" class="mdl-slider mdl-js-slider" type="range"
                           min="0" max="30" value="${endDate}" tabindex="0">
                </div>
                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label getmdl-select getmdl-select__fullwidth">
                    <input class="mdl-textfield__input" type="text" id="sample1" readonly tabIndex="-1">
                    <label for="sample1" class="mdl-textfield__label">지역</label>
                    <nav>
                        <ul id="city-list-ul" for="sample1" class="mdl-menu mdl-menu--bottom-left mdl-js-menu">
                            <li class="mdl-menu__item city-list" value="">전체</li>
                            <li class="mdl-menu__item city-list" value="6110000">서울특별시</li>
                            <li class="mdl-menu__item city-list" value="6410000">경기도</li>
                            <li class="mdl-menu__item city-list" value="6260000">부산광역시</li>
                            <li class="mdl-menu__item city-list" value="6270000">대구광역시</li>
                            <li class="mdl-menu__item city-list" value="6280000">인천광역시</li>
                            <li class="mdl-menu__item city-list" value="6290000">광주광역시</li>
                            <li class="mdl-menu__item city-list" value="6300000">대전광역시</li>
                            <li class="mdl-menu__item city-list" value="6310000">울산광역시</li>
                            <li class="mdl-menu__item city-list" value="6420000">강원도</li>
                            <li class="mdl-menu__item city-list" value="6430000">충청북도</li>
                            <li class="mdl-menu__item city-list" value="6440000">충청남도</li>
                            <li class="mdl-menu__item city-list" value="6450000">전라북도</li>
                            <li class="mdl-menu__item city-list" value="6460000">전라남도</li>
                            <li class="mdl-menu__item city-list" value="6470000">경상북도</li>
                            <li class="mdl-menu__item city-list" value="6480000">경상남도</li>
                            <li class="mdl-menu__item city-list" value="6500000">제주특별자치도</li>
                        </ul>
                    </nav>
                </div>
                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label getmdl-select getmdl-select__fullwidth">
                    <input class="mdl-textfield__input" type="text" id="sample2" readonly tabIndex="-1">
                    <label for="sample2" class="mdl-textfield__label">품종</label>
                    <ul for="sample2" class="mdl-menu mdl-menu--bottom-left mdl-js-menu">
                        <li class="mdl-menu__item animal-list" value="">동물</li>
                        <li class="mdl-menu__item animal-list" value="417000">강아지</li>
                        <li class="mdl-menu__item animal-list" value="422400">고양이</li>
                    </ul>
                </div>
                <div id="filter-list">
                    <label id="city-filter"></label>
                    <label id="period-filter"></label>
                    <label id="animal-filter">길을 잃은 <a href="#">동물</a>들을 보여주세요</label>
                </div>
            </div>
            <div class="mdl-card__actions mdl-card--border" style="text-align:center;">
                <form id="searchForm">
                    <input type="hidden" id="cityCode" name="cityCode" value="${cityCode}">
                    <input type="hidden" id="cityName" name="cityName" value="${cityName}">
                    <input type="hidden" id="upKindCode" name="upKindCode" value="${upKindCode}">
                    <input type="hidden" id="upKindName" name="upKindName" value="${upKindName}">
                    <input type="hidden" id="endDate" name="endDate" value="${endDate}">
                    <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                            data-upgraded=",MaterialButton,MaterialRipple" id="search-btn">
                        SEARCH
                        <span class="mdl-button__ripple-container"><span class="mdl-ripple"></span></span></button>
                </form>
                <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                        data-upgraded=",MaterialButton,MaterialRipple" id="close-btn">
                    CLOSE
                    <span class="mdl-button__ripple-container"><span class="mdl-ripple"></span></span></button>
            </div>
        </dialog>
        <button id="view-source"
                class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored mdl-shadow--4dp mdl-color--accent mdl-button mdl-button--raised"
                data-upgraded=",MaterialButton,MaterialRipple">
            <i class="material-icons" role="presentation">search</i>
            <span class="visuallyhidden">Search</span>
            <span class="mdl-button__ripple-container"><span class="mdl-ripple is-animating"
                                                             style="width: 160.392px; height: 160.392px; transform: translate(-50%, -50%) translate(29px, 41px);"></span></span>
        </button>
        <button id="demo-show-snackbar" class="mdl-button mdl-js-button mdl-button--raised" type="button"
                style="display:none;">
        </button>
        <div id="demo-snackbar-example" class="mdl-js-snackbar mdl-snackbar">
            <div class="mdl-snackbar__text"></div>
            <button class="mdl-snackbar__action" type="button"></button>
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
    <div class="mdl-menu__container is-upgraded" style="right: 235.984px; top: 344px; width: 124px; height: 208px;">
        <div class="mdl-menu__outline mdl-menu--bottom-right" style="width: 124px; height: 208px;"></div>
        <ul class="mdl-menu mdl-js-menu mdl-menu--bottom-right mdl-js-ripple-effect mdl-js-ripple-effect--ignore-events"
            for="menubtn" data-upgraded=",MaterialMenu,MaterialRipple" style="clip: rect(0px 124px 0px 124px);">
            <li class="mdl-menu__item city-list mdl-js-ripple-effect" tabindex="-1" data-upgraded=",MaterialRipple"
                style="">About<span
                    class="mdl-menu__item city-list-ripple-container"><span class="mdl-ripple"></span></span></li>
            <li class="mdl-menu__item city-list mdl-js-ripple-effect" tabindex="-1" data-upgraded=",MaterialRipple"
                style="">
                Message<span class="mdl-menu__item city-list-ripple-container"><span class="mdl-ripple"></span></span>
            </li>
            <li class="mdl-menu__item city-list mdl-js-ripple-effect" tabindex="-1" data-upgraded=",MaterialRipple"
                style="">
                Favorite<span class="mdl-menu__item city-list-ripple-container"><span class="mdl-ripple"></span></span>
            </li>
            <li class="mdl-menu__item city-list mdl-js-ripple-effect" tabindex="-1" data-upgraded=",MaterialRipple"
                style="">
                Search<span class="mdl-menu__item city-list-ripple-container"><span class="mdl-ripple"></span></span>
            </li>
        </ul>
    </div>
</div>
<script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/dialog-polyfill/0.4.9/dialog-polyfill.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/lazyload@2.0.0-beta.2/lazyload.js"></script>
<script>
    (function () {
        'use strict';
        var dialog = document.querySelector('#modal-example');
        var formEl = document.querySelector('#searchForm');
        var searchButton = dialog.querySelector('#search-btn');
        var closeButton = dialog.querySelector('#close-btn');
        var showButton = document.querySelector('#view-source');
        var cities = dialog.getElementsByClassName('city-list');
        var animals = dialog.getElementsByClassName('animal-list');
        var period = dialog.querySelector('#search-period');
        if (!dialog.showModal) {
            dialogPolyfill.registerDialog(dialog);
        }
        var closeClickHandler = function (event) {
            dialog.close();
        };
        var generateMessage = function () {
            let sidoName = document.querySelector('#cityName').value;
            if (cityName != '') {
                document.querySelector('#city-filter').innerHTML = '<a href="#">' + cityName + '</a>에서 ';
            }
            let endDate = document.querySelector('#endDate').value;
            if (endDate != '' && endDate != '0') {
                document.querySelector('#period-filter').innerHTML = '<a href="#">' + endDate + '</a>일전부터 ';
            }
            let upKindName = document.querySelector('#upKindName').value;
            if (upKindName != '') {
                document.querySelector('#animal-filter').innerHTML = '길을 잃은 <a href="#">' + upKindName + '</a>들을 보여주세요';
            }
            else {
                document.querySelector('#animal-filter').innerHTML = '길을 잃은 <a href="#">동물</a>들을 보여주세요';
            }
        }
        var showClickHandler = function (event) {
            generateMessage();
            dialog.showModal();
        };
        var cityListClickHandler = function (event) {
            let cityName = event.target.innerHTML;
            document.querySelector('#cityCode').value = event.target.value;
            document.querySelector('#cityName').value = event.target.innerText;
            if (cityName == '전체') {
                document.querySelector('#city-filter').innerHTML = '';
            }
            else {
                document.querySelector('#city-filter').innerHTML = '<a href="#">' + cityName + '</a>에서 ';
            }
        };
        var animalListClickHandler = function (event) {
            let animalName = event.target.innerHTML;
            document.querySelector('#upKindCode').value = event.target.value;
            document.querySelector('#upKindName').value = event.target.innerText;
            document.querySelector('#animal-filter').innerHTML = '길을 잃은 <a href="#">' + animalName + '</a>들을 보여주세요';
        };
        var periodChangeHandler = function (event) {
            let period = event.target.value;
            document.querySelector('#endDate').value = period;
            if (period == '0') {
                document.querySelector('#period-filter').innerHTML = '';
            }
            else {
                document.querySelector('#period-filter').innerHTML = '<a href="#">' + period + '</a>일전부터 ';
            }
        };
        showButton.addEventListener('click', showClickHandler);
        formEl.addEventListener('submit', closeClickHandler);
        closeButton.addEventListener('click', closeClickHandler);
        for (let city of cities) {
            city.addEventListener('click', cityListClickHandler);
        }
        for (let animal of animals) {
            animal.addEventListener('click', animalListClickHandler);
        }
        period.addEventListener('change', periodChangeHandler);

        // snackbar
        var snackbarContainer = document.querySelector('#demo-snackbar-example');
        var showSnackbarButton = document.querySelector('#demo-show-snackbar');
        var handler = function (event) {
            showSnackbarButton.style.backgroundColor = '';
        };
        showSnackbarButton.addEventListener('click', function () {
            'use strict';
            generateMessage();
            var msg = document.querySelector('#filter-list').innerText;
            msg = msg.replace('들을 보여주세요', '들 입니다');
            var data = {
                message: msg,
                timeout: 3000
            };
            snackbarContainer.MaterialSnackbar.showSnackbar(data);
        });
        window.onload = function () {
            showSnackbarButton.click();
        };
        // snackbar end

        // lazy
        let images = document.querySelectorAll(".article-images");
        lazyload(images);
        // lazy end
    }());
</script>
</body>
</html>
