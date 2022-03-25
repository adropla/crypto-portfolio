import { EyeInvisibleOutlined, EyeTwoTone } from '@ant-design/icons';
import { Button, Checkbox, Form, Input, Typography } from 'antd';
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
    const { loginTrigger, email, handleEmail, password, handlePassword } =
        useAuthentification();

    const dispatch = useAppDispatch();

    const [form] = Form.useForm();

    const handleOk = () => {
        toogleLoginModal();
    };

    const onFinish = async () => {
        const data = await loginTrigger({ email, password }).unwrap();
        dispatch(setCredentials({ ...data, email }));
    };

    const toSignUpModal = () => {
        toogleSignUpModal();
        toogleLoginModal();
        form.resetFields();
    };

    const toForgotPasswordModal = () => {
        toogleLoginModal();
        toogleForgotModal();
        form.resetFields();
    };

    const onCancel = () => {
        toogleLoginModal();
        form.resetFields();
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
