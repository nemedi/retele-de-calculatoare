function Fetch() {
    $.ajax({
        type: 'GET',
        url: '!contacts',
        success: function(result) {
            var contacts = JSON.parse(result);
            var html = '<table>';
            $.each(contacts, function (index, contact) {
                html += '<tr>'
                    + '<td nowrap>First Name</td>'
                    + '<td width="50%"><input id="FirstName' + contact.Id + '" value="' + contact.FirstName + '" style="width:100%"></td>'
                    + '<td nowrap>Last Name</td>'
                    + '<td width="50%"><input id="LastName' + contact.Id + '" value="' + contact.LastName + '" style="width:100%"></td>'
                    + '<td><input type="button" value="Change" onclick="Change(' + contact.Id + ')"></td>'
                    + '<td><input type="button" value="Remove" onclick="Remove(' + contact.Id + ')"></td>'
                    + '</tr>';
            });
            html += '</table>';
            $('#Contacts').html(html);
        }
    });
}

function Add() {
    var contact = {};
    contact.FirstName = $('#FirstName0').val();
    contact.LastName = $('#LastName0').val();
    if (contact.FirstName.length > 0 && contact.LastName.length > 0) {
        $.ajax({
            type: 'POST',
            url: '!contacts',
            data: JSON.stringify(contact),
            success: function() {
                Fetch();
            }
        });
    }
}

function Change(id) {
    var contact = {};
    contact.Id = id;
    contact.FirstName = $('#FirstName' + id).val();
    contact.LastName = $('#LastName' + id).val();
    if (contact.FirstName.length > 0 && contact.LastName.length > 0) {
        $.ajax({
            type: 'PUT',
            url: '!contacts',
            data: JSON.stringify(contact),
            success: function () {
                Fetch();
            }
        });
    }
}

function Remove(id) {
    $.ajax({
        type: 'DELETE',
        url: '!contacts',
        data: JSON.stringify(id),
        success: function() {
            Fetch();
        }
    });
}

$(document).ready(function() {
    Fetch();
});