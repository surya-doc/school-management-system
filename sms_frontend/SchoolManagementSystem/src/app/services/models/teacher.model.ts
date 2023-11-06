export interface Teacher {
    id: number;
    fullName: string;
    dob: string;
    email: string;
    phoneNumber: string;
    qualification: string,
    subjects: string[],
    classes: string[],
    gender: string,
    current_Address: {
      street_address: string;
      city: string;
      state: string;
      postal_code: string;
      country: string;
    };
    permanent_Address: {
      street_address: string;
      city: string;
      state: string;
      postal_code: string;
      country: string;
    };
    profilePic: string;
}
  