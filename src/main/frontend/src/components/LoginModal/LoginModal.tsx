import { EyeInvisibleOutlined, EyeTwoTone } from '@ant-design/icons';
import { Button, Checkbox, Form, Input, Typography } from 'antd';
import { FocusEventHandler, useState } from 'react';
import { Link } from 'react-router-dom';
import useAuthentification from '../../hooks/useAuthentification';
import RoundModal from '../../styledComponents/RoundModal';
import { LoginModalProps } from '../../types/ModalProps';

import styles from './LoginModal.module.scss';

const { Text } = Typography;

export const inputPasswordIconRender = (passVisible: boolean) =>
    passVisible ? <EyeTwoTone /> : <EyeInvisibleOutlined />;

const LoginModal = ({
    visible,
    toogleLoginModal,
    toogleSignUpModal,
    toogleForgotModal,
}: LoginModalProps) => {
    const {
        loginTrigger,
        loginResult,
        email,
        handleEmail,
        password,
        handlePassword,
    } = useAuthentification();

    const handleOk = () => {
        toogleLoginModal();
    };

    const onFinish = () => {
        loginTrigger({ email, password });
        console.log(loginResult);
    };

    const toSignUpModal = () => {
        toogleSignUpModal();
        toogleLoginModal();
    };

    const toForgotPasswordModal = () => {
        toogleLoginModal();
        toogleForgotModal();
    };

    return (
        <RoundModal
            radius="5px"
            centered
            visible={visible}
            bodyStyle={{ borderRadius: '50px' }}
            onOk={handleOk}
            onCancel={() => toogleLoginModal()}
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
                        Login
                    </Text>
                </Form.Item>
                <Form.Item noStyle>
                    <Form.Item>
                        <Text style={{ fontSize: '14px', color: 'darkgray' }}>
                            Don&apos;t have a account?
                        </Text>
                        <Button
                            type="link"
                            onClick={toSignUpModal}
                            style={{ fontSize: '14px', padding: '0 0 0 6px' }}
                        >
                            Sign Up!
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
                    <Input placeholder="Email" onChange={handleEmail} />
                </Form.Item>
                <Form.Item
                    name="password"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your Password!',
                        },
                    ]}
                >
                    <Input.Password
                        onChange={handlePassword}
                        type="password"
                        placeholder="Password"
                        iconRender={inputPasswordIconRender}
                    />
                </Form.Item>
                <Form.Item name="remember" valuePropName="checked">
                    <div className={styles.flexWrapper}>
                        <Button
                            onClick={toForgotPasswordModal}
                            className={styles.forgotLink}
                            type="link"
                        >
                            Forgot password?
                        </Button>
                        <Checkbox>Remember me</Checkbox>
                    </div>
                </Form.Item>

                <Form.Item noStyle>
                    <Button
                        type="primary"
                        htmlType="submit"
                        className={styles.btn}
                    >
                        Log in
                    </Button>
                </Form.Item>
            </Form>
        </RoundModal>
    );
};

export default LoginModal;
