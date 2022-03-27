import { FocusEventHandler, useEffect, useState } from 'react';
import { useLoginMutation, useSignupMutation } from '../services/serverApi';

const useAuthentification = () => {
    const [loginTrigger, loginResult] = useLoginMutation();
    const [signUpTrigger, signUpResult] = useSignupMutation();
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const [error, setError] = useState<boolean>(false);

    const handlePassword: FocusEventHandler<HTMLInputElement> = (e) => {
        setPassword(e.target.value);
        setError(false);
    };

    const handleEmail: FocusEventHandler<HTMLInputElement> = (e) => {
        setEmail(e.target.value);
        setError(false);
    };

    useEffect(() => {
        setError(loginResult.isError);
    }, [loginResult]);

    return {
        loginTrigger,
        signUpTrigger,
        email,
        handleEmail,
        password,
        handlePassword,
        loginResult,
        signUpResult,
        error,
        setError,
    };
};

export default useAuthentification;
