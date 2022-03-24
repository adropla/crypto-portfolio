import { Button, Form, Input, Typography } from 'antd';
import { useState } from 'react';
import useAuthentification from '../../hooks/useAuthentification';
import RoundModal from '../../styledComponents/RoundModal';
import { SignupModalProps } from '../../types/ModalProps';
import { inputPasswordIconRender } from '../LoginModal/LoginModal';

import styles from './SignUpModal.module.scss';

const { Text } = Typography;

const SignUpModal = ({
    visible,
    toogleLoginModal,
    toogleSignUpModal,
}: SignupModalProps) => {
    const {
        email,
        password,
        handleEmail,
        handlePassword,
        handleSecondPassword,
    } = useAuthentification();

    const handleOk = () => {
        toogleSignUpModal();
    };

    const onFinish = () => {};

    const toLoginModal = () => {
        toogleSignUpModal();
        toogleLoginModal();
    };

    return (
        <RoundModal
            radius="5px"
            centered
            visible={visible}
            wrapClassName={styles.modalWrapper}
            bodyStyle={{ borderRadius: '50px' }}
            onOk={handleOk}
            onCancel={() => toogleSignUpModal()}
            footer={null}
        >
            <Form
                name="normal_login"
                className="login-form"
                initialValues={{ remember: true }}
                onFinish={onFinish}
            >
                <Form.Item noStyle>
                    <Text style={{ fontSize: '25px', fontWeight: 'bold' }}>
                        Sign Up
                    </Text>
                </Form.Item>
                <Form.Item noStyle>
                    <Form.Item>
                        <Text style={{ fontSize: '14px', color: 'darkgray' }}>
                            Already have an account?
                        </Text>
                        <Button
                            type="link"
                            onClick={toLoginModal}
                            style={{ fontSize: '14px', padding: '0 0 0 6px' }}
                        >
                            Log In!
                        </Button>
                    </Form.Item>
                </Form.Item>

                <Form.Item
                    name="email"
                    rules={[
                        {
                            type: 'email',
                            message: 'Please input correct Email!',
                            validateTrigger: 'onSubmit',
                        },
                        {
                            required: true,
                            message: 'Please input your Email!',
                            validateTrigger: 'onSubmit',
                        },
                    ]}
                >
                    <Input
                        placeholder="Enter your email address"
                        onChange={handleEmail}
                    />
                </Form.Item>
                <Form.Item
                    name="password1"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your Password!',
                        },
                    ]}
                >
                    <Input.Password
                        type="password"
                        placeholder="Password"
                        iconRender={inputPasswordIconRender}
                        onChange={handlePassword}
                    />
                </Form.Item>
                <Form.Item
                    name="password2"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your Password!',
                        },
                    ]}
                >
                    <Input.Password
                        type="password"
                        placeholder="Password"
                        iconRender={inputPasswordIconRender}
                        onChange={handleSecondPassword}
                    />
                </Form.Item>

                <Form.Item noStyle>
                    <Button
                        type="primary"
                        htmlType="submit"
                        className={styles.btn}
                    >
                        Sign Up
                    </Button>
                </Form.Item>
            </Form>
        </RoundModal>
    );
};

export default SignUpModal;
