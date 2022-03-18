import { Button, Form, Input, Typography } from 'antd';
import RoundModal from '../../styledComponents/RoundModal';
import { ModalProps } from '../../types/ModalProps';
import { inputPasswordIconRender } from '../LoginModal/LoginModal';

import styles from './SignUpModal.module.scss';

const { Text } = Typography;

const SignUpModal = ({
    visible,
    toogleLoginModal,
    toogleSignUpModal,
}: ModalProps) => {
    const handleOk = () => {
        toogleSignUpModal();
    };

    const onFinish = (values: any) => {
        console.log('Received values of form: ', values);
    };

    const toLoginModal = () => {
        toogleSignUpModal();
        toogleLoginModal();
    };

    return (
        <RoundModal
            radius="15px"
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
                            required: true,
                            message: 'Please input your Email!',
                        },
                    ]}
                >
                    <Input placeholder="Enter your email address" />
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
                        type="password"
                        placeholder="Password"
                        iconRender={inputPasswordIconRender}
                    />
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
