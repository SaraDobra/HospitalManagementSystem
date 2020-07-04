var formFields= {

    submit:document.getElementById("submit"),
    form: document.getElementById("form"),
    firstName:document.getElementById("firstName"),
    lastName: document.getElementById("lastName"),
    email: document.getElementById("email"),
    phoneNr: document.getElementById("phoneNr"),
    username: document.getElementById("username"),
    password: document.getElementById("password")
};

var g_form = new Form();

formFields.firstName.addEventListener("input", function(){
    g_form.validateName(formFields.firstName);
});

formFields.lastName.addEventListener("input", function(){
    g_form.validateLastName(formFields.lastName);
});

formFields.email.addEventListener("input", function(){
    g_form.validateEmail(formFields.email);
});

formFields.phoneNr.addEventListener("input", function(){
    g_form.validatePhoneNr(formFields.phoneNr);
});

formFields.username.addEventListener("input", function(){
    g_form.validateUserName(formFields.username);
});

formFields.password.addEventListener("input", function(){
    g_form.validatePassword(formFields.password);
});


formFields.form.addEventListener("submit", function(event){

    console.log(g_form.validateName(formFields.firstName));
    console.log(g_form.validateLastName(formFields.lastName));
    console.log(g_form.validateEmail(formFields.email));
    console.log(g_form.validateDate(formFields.phoneNr));
    console.log(g_form.validatePhoneNr(formFields.username));
    console.log(g_form.validateAddress(formFields.password));



    if(!g_form.validateEmail(formFields.email) || !g_form.validateName(formFields.firstName)
        || !g_form.validateLastName(formFields.lastName)
        || !g_form.validatePhoneNr(formFields.phoneNr)
        || !g_form.validatePassword(formFields.password)
        || !g_form.validateUserName(formFields.username)){
        event.preventDefault();
    }else{
        $(this).unbind( event );
    }
});