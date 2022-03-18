import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Checkbox, Form, Input } from 'antd';
import { Link } from 'react-router-dom';
import RoundModal from '../../styledComponents/RoundModal';
import { ModalProps } from '../../types/ModalProps';

import styles from './LoginModal.module.scss';

const LoginModal = ({ visible, toogleModal }: ModalProps) => {
    const handleOk = () => {
        toogleModal();
    };

    const onFinish = (values: any) => {
        console.log('Received values of form: ', values);
    };

    return (
        <RoundModal
            radius="15px"
            centered
            visible={visible}
            wrapClassName={styles.modalWrapper}
            bodyStyle={{ borderRadius: '50px' }}
            onOk={handleOk}
            onCancel={() => toogleModal()}
            footer={null}
        >
            <Form
                name="normal_login"
                className="login-form"
                initialValues={{ remember: true }}
                onFinish={onFinish}
            >
                <Form.Item
                    name="email"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your Email!',
                        },
                    ]}
                >
                    <Input
                        prefix={
                            <UserOutlined className="site-form-item-icon" />
                        }
                        placeholder="Email"
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
                    <Input
                        prefix={
                            <LockOutlined className="site-form-item-icon" />
                        }
                        type="password"
                        placeholder="Password"
                    />
                </Form.Item>
                <Form.Item name="remember" valuePropName="checked">
                    <Checkbox>Remember me</Checkbox>
                </Form.Item>

                <Form.Item>
                    <Link className="login-form-forgot" to="/">
                        Forgot password
                    </Link>
                </Form.Item>

                <Form.Item>
                    <Button
                        type="primary"
                        htmlType="submit"
                        className="login-form-button"
                    >
                        Log in
                    </Button>
                </Form.Item>
                <Form.Item>
                    Or <Link to="/">register now!</Link>
                </Form.Item>
            </Form>
        </RoundModal>
    );
};

export default LoginModal;
