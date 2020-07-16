function Form() {
    this.validateName = function (name) {
        var nameRegex = /\d/;
        return (name.value.length > 2 && !nameRegex.test(name.value)) ? this.validInput(name) : this.invalidInput(name);
    };

    this.validateUserName = function (userName) {
        var nameRegex = /\d/;
        return (userName.value.length > 2 && !nameRegex.test(userName.value)) ? this.validInput(userName) : this.invalidInput(userName);
    };

    this.validatePassword = function (password) {
        var patt = new RegExp(/^[a-zA-Z0-9\s,'-]*$/);
        return (password.value.length > 8 && patt.test(password.value)) ? this.validInput(password) : this.invalidInput(password);
    };

    this.validateLastName = function (lastName) {
        var nameRegex = /\d/;
        return (lastName.value.length > 2 && !nameRegex.test(lastName.value)) ? this.validInput(lastName) : this.invalidInput(lastName);
    };

    this.validateEmail = function (email) {
        var emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return (emailRegex.test(String(email.value).toLowerCase())) ? this.validInput(email) : this.invalidInput(email);
    };

    this.validateId = function (id) {
        console.log(id.value)
        return id.value.length === 10 ? this.validInput(id) : this.invalidInput(id);
    };

    this.validateDate = function (date) {
        var dateParts = date.value.split("-");
        var year = dateParts[0];
        var month = dateParts[1];
        var day = dateParts[2];
        console.log(year+","+month+","+day);
        var currentDate = new Date();
        var currentYear = currentDate.getFullYear();
        return (day > 1 && day < 32 && month > 1 && month < 12 && year > 1000 && year <= currentYear) ? this.validInput(date) : this.invalidInput(date);
    };

    this.validateDateTime = function(dt){
      return Date.parse(dt.value) ? this.validInput(dt) : this.invalidInput(dt)
    };


    this.invalidInput = function (element) {
        element.style.border = "2px solid red";
        return false
    };

    this.validInput = function (element) {
        element.style.border = "2px solid green";
        return true
    };

    this.validatePhoneNr = function (phoneNr) {
        var patt = new RegExp(/^\d{9}$/);
        return patt.test(phoneNr.value) ? this.validInput(phoneNr) : this.invalidInput(phoneNr);
    };

    this.validateAddress = function (address) {
        var patt = new RegExp(/^[a-zA-Z0-9\s,'-]*$/);
        return (patt.test(address.value) && address.value.length > 5) ? this.validInput(address) : this.invalidInput(address);
    };

    this.validateCity = function (city) {
        var nameRegex = /\d/;
        return (city.value.length > 2 && !nameRegex.test(city.value)) ? this.validInput(city) : this.invalidInput(city);
    };

    this.validateCountry = function (country) {
        var nameRegex = /\d/;
        return (country.value.length > 2 && !nameRegex.test(country.value)) ? this.validInput(country) : this.invalidInput(country);
    };

    this.validateAppointmentTitle = function (appointmentTitle) {
        var nameRegex = /\d/;
        return (appointmentTitle.value.length > 2 && !nameRegex.test(appointmentTitle.value)) ? this.validInput(appointmentTitle) : this.invalidInput(appointmentTitle);
    };
    this.validateAppointmentDescription = function (appointmentDescription) {
        var nameRegex = /\d/;
        return (appointmentDescription.value.length > 2 && !nameRegex.test(appointmentDescription.value)) ? this.validInput(appointmentDescription) : this.invalidInput(appointmentDescription);
    };

    this.validatePatientName = function (patientName) {
        var nameRegex = /^[A-Za-z0-9]+ [A-Za-z0-9]+$/;
        return (nameRegex.test(patientName.value)) ? this.validInput(patientName) : this.invalidInput(patientName);
    };
    this.validateDoctorName= function (doctorName) {
        var nameRegex = /^[A-Za-z0-9]+ [A-Za-z0-9]+$/;
        return (nameRegex.test(doctorName.value)) ? this.validInput(doctorName) : this.invalidInput(doctorName);

    }
}