import {
    EyeInvisibleOutlined,
    EyeTwoTone,
    LockOutlined,
    UserOutlined,
} from '@ant-design/icons';
import { Button, Checkbox, Form, Input, Typography } from 'antd';
import { Link } from 'react-router-dom';
import RoundModal from '../../styledComponents/RoundModal';
import { ModalProps } from '../../types/ModalProps';

import styles from './LoginModal.module.scss';

const { Text } = Typography;

const inputPasswordIconRender = (passVisible: boolean) =>
    passVisible ? <EyeTwoTone /> : <EyeInvisibleOutlined />;

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
                        <Link to="/" style={{ fontSize: '14px' }}>
                            {' '}
                            Register now!
                        </Link>
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
                    <Input prefix={<UserOutlined />} placeholder="Email" />
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
                        prefix={<LockOutlined />}
                        type="password"
                        placeholder="Password"
                        iconRender={inputPasswordIconRender}
                    />
                </Form.Item>
                <Form.Item name="remember" valuePropName="checked">
                    <div className={styles.flexWrapper}>
                        <Link className={styles.forgotLink} to="/">
                            Forgot password?
                        </Link>
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
