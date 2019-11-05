function post() {
    var username = $("#username").val();
    var password = $("#password").val();
    // console.log(questionId);
    // console.log(content);
    $.ajax({
        type: "POST",
        url: "/login",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": username,
            "content": password
        }),
        success: function () {
            console.log("成功")
        },
        dataType: "json"
    });
}