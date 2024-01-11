const toggleSideBar = () => {
    if ($(".sidebar").is(":visible")) {
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "20px")
    } else {
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "20%")
    }
};