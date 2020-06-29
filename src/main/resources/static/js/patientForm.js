var formFields = {
    id: document.getElementById("idNr"),
    firstName: document.getElementById("firstName"),
    lastName: document.getElementById("lastName"),
    email: document.getElementById("email"),
    phoneNr: document.getElementById("phoneNr"),
    address:document.getElementById("address"),
    city:document.getElementById("city"),
    country:document.getElementById("country"),
    date: document.getElementById("birthDate"),
    submit:document.getElementById("submit"),
    form: document.getElementById("form"),
};
var g_form = new Form();

formFields.id.addEventListener("input", function(){
    g_form.validateId(formFields.id);
});

formFields.email.addEventListener("input", function(){
    g_form.validateEmail(formFields.email);
});

formFields.firstName.addEventListener("input", function(){
    g_form.validateName(formFields.firstName);
});
formFields.phoneNr.addEventListener("input", function(){
    g_form.validatePhoneNr(formFields.phoneNr);
});
formFields.date.addEventListener("change", function(){
    g_form.validateDate(formFields.date);
});

formFields.lastName.addEventListener("input", function(){
    g_form.validateLastName(formFields.lastName);
});

formFields.address.addEventListener("input", function(){
    g_form.validateAddress(formFields.address);
});
formFields.city.addEventListener("input", function(){
    g_form.validateCity(formFields.city);
});
formFields.country.addEventListener("input", function(){
    g_form.validateCountry(formFields.country);
});

formFields.form.addEventListener("submit", function(event){

    console.log(g_form.validateId(formFields.id));
    console.log(g_form.validateName(formFields.firstName));
    console.log(g_form.validateLastName(formFields.lastName));
    console.log(g_form.validateEmail(formFields.email));
    console.log(g_form.validateDate(formFields.date));
    console.log(g_form.validatePhoneNr(formFields.phoneNr));
    console.log(g_form.validateAddress(formFields.address));
    console.log(g_form.validateCity(formFields.city));
    console.log(g_form.validateCountry(formFields.country));

    if(!g_form.validateId(formFields.id)
        || !g_form.validateEmail(formFields.email)
        || !g_form.validateName(formFields.firstName)
        || !g_form.validateLastName(formFields.lastName)
        || !g_form.validatePhoneNr(formFields.phoneNr)
        || !g_form.validateDate(formFields.date)
        || !g_form.validateAddress(formFields.address)
        || !g_form.validateCity(formFields.city)
        || !g_form.validateCountry(formFields.country)){
        event.preventDefault();
    }else{
        $(this).unbind( event );
    }
});