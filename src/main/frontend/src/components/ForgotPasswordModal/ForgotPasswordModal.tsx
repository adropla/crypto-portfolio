import {
    ArrowLeftOutlined,
    EyeInvisibleOutlined,
    EyeTwoTone,
} from '@ant-design/icons';
import { Button, Checkbox, Form, Input, Typography } from 'antd';
import { FocusEventHandler, useState } from 'react';
import { Link } from 'react-router-dom';
import useAuthentification from '../../hooks/useAuthentification';
import { useLoginMutation } from '../../services/serverApi';
import RoundModal from '../../styledComponents/RoundModal';
import { ForgotPasswordModalProps } from '../../types/ModalProps';

import styles from './ForgotPasswordModal.module.scss';

const { Text } = Typography;

export const inputPasswordIconRender = (passVisible: boolean) =>
    passVisible ? <EyeTwoTone /> : <EyeInvisibleOutlined />;

const ForgotPasswordModal = ({
    visible,
    toogleLoginModal,
    toogleForgotModal,
}: ForgotPasswordModalProps) => {
    const { loginTrigger, email, handleEmail, password } =
        useAuthentification();

    const [form] = Form.useForm();

    const handleOk = () => {
        toogleLoginModal();
    };

    const onFinish = () => {
        loginTrigger({ email, password });
    };

    const toFinishModal = () => {
        toogleForgotModal();
        toogleLoginModal();
    };

    const toLoginModal = () => {
        toogleForgotModal();
        toogleLoginModal();
        form.resetFields();
    };

    const onCancel = () => {
        toogleForgotModal();
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
                name="forgot_form"
                className="login-form"
                initialValues={{ remember: true }}
                onFinish={onFinish}
            >
                <div>
                    <Button
                        type="link"
                        onClick={toLoginModal}
                        style={{ fontSize: '14px', padding: '0 0 0 6px' }}
                        className={styles.arrowBtn}
                    >
                        <ArrowLeftOutlined style={{ fontSize: '18px' }} />
                    </Button>
                </div>
                <Form.Item noStyle>
                    <Text
                        style={{
                            fontSize: '25px',
                            fontWeight: 'bold',
                            marginLeft: '25px',
                        }}
                    >
                        Forgot password?
                    </Text>
                </Form.Item>
                <Form.Item noStyle>
                    <Form.Item style={{ marginBottom: 0 }}>
                        <Text style={{ fontSize: '14px', color: 'darkgray' }}>
                            Enter your email below, you will receive an email
                            with instructions.
                        </Text>
                    </Form.Item>
                </Form.Item>
                <Form.Item
                    name="email_forgot"
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
                <Form.Item noStyle>
                    <Button
                        type="primary"
                        htmlType="submit"
                        className={styles.btn}
                    >
                        Change password
                    </Button>
                </Form.Item>
            </Form>
        </RoundModal>
    );
};

export default ForgotPasswordModal;
