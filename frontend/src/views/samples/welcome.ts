//import {computedFrom} from 'aurelia-framework';
import {TaskQueue, autoinject} from 'aurelia-framework';
import {Materialize} from "../materialize";
import {PatientService} from "../../models/patient/patient-service";
import {Patient} from "../../models/patient/patient";

@autoinject
export class Welcome extends Materialize{
  heading = 'Welcome to the Aurelia Navigation App!';
  firstName = 'John';
  lastName = 'Doe';
  previousValue = this.fullName;

  patients: Array<Patient>;
  patientService: PatientService;

  constructor(patientService: PatientService){
      super();
      this.patientService = patientService;
  }

  //Getters can't be directly observed, so they must be dirty checked.
  //However, if you tell Aurelia the dependencies, it no longer needs to dirty check the property.
  //To optimize by declaring the properties that this getter is computed from, uncomment the line below
  //as well as the corresponding import above.
  //@computedFrom('firstName', 'lastName')
  get fullName() {
    return `${this.firstName} ${this.lastName}`;
  }

  submit() {
    this.previousValue = this.fullName;
    alert(`Welcome, ${this.fullName}!`);
  }

  canDeactivate() {
    if (this.fullName !== this.previousValue) {
      return confirm('Are you sure you want to leave?');
    }
  }

  loadPatients(){
    this.patientService.list("m").then(p => {

        this.patients = p;

    });
  }

}

export class UpperValueConverter {
  toView(value) {
    return value && value.toUpperCase();
  }
}
