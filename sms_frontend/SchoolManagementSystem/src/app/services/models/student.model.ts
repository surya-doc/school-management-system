export interface Student {
    id: number;
    fullName: string;
    dob: string;
    email: string;
    phoneNumber: string;
    gender: string;
    classId: number;
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
    hobbies: string[];
    profilePic: string;
}
  