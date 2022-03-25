import { Button, Form, Input, Typography } from 'antd';
import { Rule } from 'antd/lib/form';
import useAuthentification from '../../hooks/useAuthentification';
import RoundModal from '../../styledComponents/RoundModal';
import { SignupModalProps } from '../../types/ModalProps';
import {
    validateMatchPasswords,
    validateRegexPassword,
} from '../../utils/validateForms';
import { inputPasswordIconRender } from '../LoginModal/LoginModal';

import styles from './SignUpModal.module.scss';

const { Text } = Typography;

const SignUpModal = ({
    visible,
    toogleLoginModal,
    toogleSignUpModal,
}: SignupModalProps) => {
    const { email, password, handleEmail, handlePassword } =
        useAuthentification();

    const [form] = Form.useForm();

    const handleOk = () => {
        toogleSignUpModal();
    };

    const onFinish = () => {};

    const toLoginModal = () => {
        toogleSignUpModal();
        toogleLoginModal();
        form.resetFields();
    };

    const onCancel = () => {
        toogleSignUpModal();
        form.resetFields();
    };

    return (
        <RoundModal
            radius="5px"
            centered
            visible={visible}
            wrapClassName={styles.modalWrapper}
            bodyStyle={{ borderRadius: '50px' }}
            onOk={handleOk}
            onCancel={onCancel}
            footer={null}
        >
            <Form
                form={form}
                name="signup_form"
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
                    name="email_signup"
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
                    dependencies={['password2']}
                    rules={[
                        {
                            required: true,
                            message: 'Please input your Password!',
                        },
                        ({ getFieldValue }) =>
                            validateMatchPasswords(getFieldValue, 'password2'),
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
                    dependencies={['password1']}
                    rules={[
                        {
                            required: true,
                            message: 'Please input your Password!',
                            validateTrigger: 'onSubmit',
                        },
                        ({ getFieldValue }) =>
                            validateMatchPasswords(getFieldValue, 'password1'),
                        ({ getFieldValue }) =>
                            validateRegexPassword(getFieldValue, 'password1'),
                    ]}
                >
                    <Input.Password
                        type="password"
                        placeholder="Password"
                        iconRender={inputPasswordIconRender}
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
