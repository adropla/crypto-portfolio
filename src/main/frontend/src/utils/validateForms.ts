/* eslint-disable no-unused-vars */
import { RuleObject } from 'antd/lib/form';
import { NamePath } from 'antd/lib/form/interface';

export const validateMatchPasswords = (
    getFieldValue: (name: NamePath) => any,
    passwordField: NamePath,
) => ({
    validator(_: RuleObject, value: any) {
        if (!value || getFieldValue(passwordField) === value) {
            return Promise.resolve();
        }
        return Promise.reject(
            new Error('The two passwords that you entered do not match!'),
        );
    },
    validateTrigger: 'onSubmit',
});

export const validateRegexPassword = (
    getFieldValue: (name: NamePath) => any,
    passwordField: NamePath,
) => ({
    validator(_: RuleObject, value: any) {
        const regex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$/;
        if (regex.test(value) && regex.test(getFieldValue(passwordField))) {
            return Promise.resolve();
        }
        return Promise.reject(
            new Error(
                `Password must contain at least 8 characters, 
                one number, one upper letter and one lower letter!`,
            ),
        );
    },
    validateTrigger: 'onSubmit',
});
