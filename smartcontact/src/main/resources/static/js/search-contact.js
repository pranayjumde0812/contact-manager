const search = () => {
    console.log("searching.....");

    let query = $("#search-input").val();

    if (query == "") {

        $(".search-result").hide();
    } else {
        // search for contact
        console.log(query);

        // sending request to server
        let url = `http://localhost:9090/user/contact/search/${query}`;

        fetch(url)
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                console.log(data);

                let text = `<div class="list-group">`;

                data.forEach((contact) => {

                    text += `<a href="/user/contact-details/0/${contact.contactId}" class="list-group-item list-group-item-action">${contact.name}</a>`;
                });

                text += `</div>`;

                // JQuery
                $(".search-result").html(text);
                $(".search-result").show();
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
    }
}