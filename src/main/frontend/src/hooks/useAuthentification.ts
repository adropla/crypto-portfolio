import { FocusEventHandler, useState } from 'react';
import { useLoginMutation, useSignupMutation } from '../services/serverApi';

const useAuthentification = () => {
    const [loginTrigger, loginResult] = useLoginMutation();
    const [signUpTrigger, signUpResult] = useSignupMutation();
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const handlePassword: FocusEventHandler<HTMLInputElement> = (e) => {
        setPassword(e.target.value);
    };

    const handleEmail: FocusEventHandler<HTMLInputElement> = (e) => {
        setEmail(e.target.value);
    };

    return {
        loginTrigger,
        signUpTrigger,
        email,
        handleEmail,
        password,
        handlePassword,
        loginResult,
        signUpResult,
    };
};

export default useAuthentification;
