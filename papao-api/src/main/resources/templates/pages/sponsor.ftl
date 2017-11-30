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
    <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>papao</title>

    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.grey-pink.min.css"/>
    <link rel="stylesheet" href="/css/styles.css"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <!-- SEO: If your mobile URL is different from the desktop URL, add a canonical link to the desktop page https://developers.google.com/webmasters/smartphone-sites/feature-phones -->
    <!--
    <link rel="canonical" href="http://www.example.com/">
    -->

    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="$$hosted_libs_prefix$$/$$version$$/material.teal-red.min.css">
    <style>
        .demo-ribbon {
            width: 100%;
            height: 40vh;
            background-color: #dedede;
            flex-shrink: 0;
        }

        .demo-main {
            margin-top: -35vh;
            flex-shrink: 0;
        }

        .demo-header .mdl-layout__header-row {
            padding-left: 40px;
        }

        .demo-container {
            max-width: 1600px;
            width: calc(100% - 16px);
            margin: 0 auto;
        }

        .demo-content {
            border-radius: 2px;
            padding: 80px 56px;
            margin-bottom: 80px;
        }

        .demo-layout.is-small-screen .demo-content {
            padding: 40px 28px;
        }

        .demo-content h3 {
            margin-top: 48px;
        }

        .demo-footer {
            padding-left: 40px;
        }

        .demo-footer .mdl-mini-footer--link-list a {
            font-size: 13px;
        }

        .none-header {
            display: none;
        }
    </style>
</head>
<body>
<div class="demo-layout mdl-layout mdl-layout--fixed-header mdl-js-layout mdl-color--grey-100">
    <div class="demo-ribbon"></div>
    <main class="demo-main mdl-layout__content" style="margin-top: -37vh;">
        <div class="demo-container mdl-grid">
            <div class="mdl-cell mdl-cell--2-col mdl-cell--hide-tablet mdl-cell--hide-phone"></div>
            <div class="demo-content mdl-color--white mdl-shadow--4dp content mdl-color-text--grey-800 mdl-cell mdl-cell--8-col">
                <div class="demo-crumbs mdl-color-text--grey-500">
                    후원하기
                </div>
                <table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp"
                       style="white-space: normal;margin-top: 30px;">
                    <thead class="none-header">
                    <tr>
                        <th class="mdl-data-table__cell--non-numeric"></th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="mdl-data-table__cell--non-numeric">Papao 에 후원하기</td>
                        <td></td>
                        <td>$1.09</td>
                    </tr>
                    <tr>
                        <td class="mdl-data-table__cell--non-numeric">보호소에 후원하기</td>
                        <td></td>
                        <td>$1.09</td>
                    </tr>
                    <tr>
                        <td class="mdl-data-table__cell--non-numeric">정기 후원하기</td>
                        <td></td>
                        <td>$1.09 / month</td>
                    </tr>
                    </tbody>
                </table>
                <div style="width: 50%;padding: 10px;margin: 80px auto auto;">
                    <button id="demo-show-toast" class="mdl-button mdl-js-button mdl-button--raised"
                            style="width:100%;background: #3f51b5;color: #fff;">
                        후원하기
                    </button>
                    <div id="demo-toast-example" class="mdl-js-snackbar mdl-snackbar">
                        <div class="mdl-snackbar__text"></div>
                        <button class="mdl-snackbar__action" type="button"></button>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>
<script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<script>
    (function () {
        'use strict';
        window['counter'] = 0;
        var snackbarContainer = document.querySelector('#demo-toast-example');
        var showToastButton = document.querySelector('#demo-show-toast');
        showToastButton.addEventListener('click', function () {
            'use strict';
            var data = {message: 'testflight 환경에서는 결제할수 없습니다'};
            snackbarContainer.MaterialSnackbar.showSnackbar(data);
        });
    }());
</script>
</body>
</html>