var formFields= {

    submit:document.getElementById("submit"),
    form: document.getElementById("form"),
    title:document.getElementById("title"),
    date_time: document.getElementById("date_time"),
    patient_name: document.getElementById("patient_name"),
    doctor_name: document.getElementById("doctor_name"),
    description: document.getElementById("description")
};

var g_form = new Form();

formFields.title.addEventListener("input", function(){
    g_form.validateAppointmentTitle(formFields.title);
});

formFields.description.addEventListener("input", function(){
    g_form.validateAppointmentDescription(formFields.description);
});

formFields.date_time.addEventListener("change", function(){
    g_form.validateDateTime(formFields.date_time);
});

formFields.form.addEventListener("submit", function(event){

    console.log(g_form.validateAppointmentTitle(formFields.title));
    console.log(g_form.validateDateTime(formFields.date_time));
    console.log(g_form.validateAppointmentDescription(formFields.description));

    if(!g_form.validateAppointmentTitle(formFields.title)
        || !g_form.validateAppointmentDescription(formFields.description)
        || !g_form.validateDateTime(formFields.date_time)){
        event.preventDefault();
    }else{
        // $(this).unbind( event );
    }
});