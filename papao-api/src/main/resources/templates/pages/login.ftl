<!doctype html>
<head>
    <meta charset="utf-8">
    <title>papao</title>
</head>

<body>
<div>
    <input type="hidden" id="country_code" value="+82"/>
    <input type="hidden" id="phone_num"/>
    <button onclick="phone_btn_onclick();">핸드폰으로 로그인</button>
    <br/>
    <input type="hidden" id="email" = value=""/>
    <button onclick="email_btn_onclick();">이메일로 로그인</button>
</div>

<form id="my_form" name="my_form" action="/dashboard/success" method="GET" style="display: none;">
    <input type="text" id="code" name="code">
    <input type="text" id="csrf_nonce" name="csrf_nonce">
    <input type="submit" value="Submit">
</form>

<!--  -->
<script src="https://sdk.accountkit.com/ko_KR/sdk.js"></script>
<script>
    // initialize Account Kit with CSRF protection
    AccountKit_OnInteractive = function () {
        AccountKit.init(
                {
                    appId: "122630828406457",
                    state: "0",
                    version: "v1.1"
                }
        );
    };

    // login callback
    function loginCallback(response) {
        console.log('loginCallback');
        console.log(response);
        if (response.status === "PARTIALLY_AUTHENTICATED") {
            document.getElementById("code").value = response.code;
            document.getElementById("csrf_nonce").value = response.state;
            document.getElementById("my_form").submit();
        }
        else if (response.status === "NOT_AUTHENTICATED") {
            // handle authentication failure
            alert('login fail');
        }
        else if (response.status === "BAD_PARAMS") {
            // handle bad parameters
            alert('login fail');
        }
    }

    // phone form submission handler
    function phone_btn_onclick() {
        var country_code = document.getElementById("country_code").value;
        var ph_num = document.getElementById("phone_num").value;
        AccountKit.login('PHONE',
                {countryCode: country_code, phoneNumber: ph_num}, // will use default values if this is not specified
                loginCallback);
    }

    // email form submission handler
    function email_btn_onclick() {
        var email_address = document.getElementById("email").value;
        AccountKit.login('EMAIL', {emailAddress: email_address}, loginCallback);
    }
</script>
</body>