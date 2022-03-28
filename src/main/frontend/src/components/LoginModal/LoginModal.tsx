import { EyeInvisibleOutlined, EyeTwoTone } from '@ant-design/icons';
import { Button, Form, Input, message, Typography } from 'antd';
import { useEffect } from 'react';
import { useAppDispatch } from '../../hooks/redux';
import useAuthentification from '../../hooks/useAuthentification';
import { setCredentials } from '../../redux/reducers/authSlice';
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
        email,
        handleEmail,
        password,
        handlePassword,
        loginResult,
        error,
        setError,
    } = useAuthentification();

    const dispatch = useAppDispatch();

    const [form] = Form.useForm();

    const handleOk = () => {
        toogleLoginModal();
    };

    const loginModalOff = () => {
        toogleLoginModal();
        form.resetFields();
    };

    const toSignUpModal = () => {
        toogleSignUpModal();
        loginModalOff();
    };

    const toForgotPasswordModal = () => {
        toogleForgotModal();
        loginModalOff();
    };

    const onCancel = () => {
        loginModalOff();
    };

    const onFinish = async () => {
        try {
            const data = await loginTrigger({ email, password }).unwrap();
            loginModalOff();
            message.success('You have successefully login!');
            dispatch(setCredentials({ ...data, email }));
            return null;
        } catch (e) {
            setError(true);
            form.validateFields();
            return null;
        }
    };

    return (
        <RoundModal
            radius="5px"
            centered
            visible={visible}
            bodyStyle={{ borderRadius: '50px' }}
            onOk={handleOk}
            onCancel={onCancel}
            footer={null}
        >
            <Form
                form={form}
                name="login_form"
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
                    name="email_login"
                    validateStatus={error ? 'error' : ''}
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
                    style={{ marginBottom: 0 }}
                    name="password"
                    validateStatus={error ? 'error' : ''}
                    rules={[
                        {
                            required: true,
                            message: 'Please input your Password!',
                            validateTrigger: 'onSubmit',
                        },
                        () => ({
                            validator() {
                                if (error) {
                                    return Promise.reject(
                                        new Error(
                                            'Email or password are incorrect',
                                        ),
                                    );
                                }
                                return Promise.resolve();
                            },
                            validateTrigger: 'onSubmit',
                        }),
                    ]}
                >
                    <Input.Password
                        onChange={handlePassword}
                        type="password"
                        placeholder="Password"
                        iconRender={inputPasswordIconRender}
                    />
                </Form.Item>
                <Form.Item name="remember" valuePropName="checked" noStyle>
                    <div className={styles.flexWrapper}>
                        <Button
                            onClick={toForgotPasswordModal}
                            className={styles.forgotLink}
                            type="link"
                        >
                            Forgot password?
                        </Button>
                    </div>
                </Form.Item>

                <Form.Item noStyle>
                    <Button
                        loading={loginResult.isLoading}
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
