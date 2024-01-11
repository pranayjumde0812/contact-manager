function deleteContact(cId, currentPage) {
    swal({
        title: "Are you sure?",
        text: "You want to delete this contact...",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
            if (willDelete) {

                window.location = "/user/contact/delete-contact/" + currentPage + "/" + cId;

            } else {
                swal("Your contact is Safe !!! ");
            }
        });
}


// sweet alert 2
function deleteContactWithSweetAlert2(contactId, currentPage) {
    Swal.fire({
        title: "Are you sure?",
        text: "You want to delete this contact permanently...",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                title: "Deleting...",
                text: "Your contact is being deleted.",
                icon: "info",
                showConfirmButton: false
            });

            // Adding a delay of 2 seconds before triggering the actual deletion
            setTimeout(() => {
                // Perform the deletion operation
                window.location = "/user/contact/delete-contact/" + currentPage + "/" + contactId;

            }, 1200); // 2000 milliseconds (2 seconds) delay

            // The deletion is complete, show a manual "Okay" button
            Swal.fire({
                title: "Deleted!",
                text: "Your contact is deleted.",
                icon: "success",
                showConfirmButton: false,
            });
        } else {

                Swal.fire({
                    title: "Cancelled",
                    text: "Your contact is safe...",
                    icon: "error",
                    showConfirmButton: false,
                    timer: 1200
                });
        }
    });
}




