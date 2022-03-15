import { Button, Card, Divider, Form, Input, Typography } from 'antd';

import styles from './AccountSetting.module.scss';

const { Text } = Typography;

const AccountSetting = () => {
    const [form] = Form.useForm();
    const username = 'user';
    const email = 'user@iuser.ru';

    return (
        <Card className={styles.card} title="Account Settings" bordered>
            <Form layout="vertical" form={form}>
                <Form.Item label="Username" className={styles.formItem}>
                    <Input value={username} />
                </Form.Item>
                <Form.Item
                    label="Email"
                    className={styles.formItem}
                    rules={[{ type: 'email' }]}
                >
                    <Input value={email} type="email" />
                </Form.Item>
                <Form.Item style={{ marginBottom: 0 }}>
                    <Button
                        type="primary"
                        htmlType="submit"
                        className={styles.btn}
                    >
                        Save
                    </Button>
                </Form.Item>
            </Form>
            <Divider />
            <div className={styles.footerWrapper}>
                <Text className={styles.footerText}>Password</Text>
                <Button type="ghost" htmlType="submit" className={styles.btn}>
                    Change password
                </Button>
            </div>
        </Card>
    );
};

export default AccountSetting;
