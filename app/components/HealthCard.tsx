import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  SafeAreaView,
  Switch,
} from 'react-native';

const HealthCard = () => {
  const [formData, setFormData] = useState({
    name: '',
    idNumber: '',
    gender: '',
    age: '',
    relationship: '',
    phone: '',
    isDefault: false,
  });

  const handleSave = () => {
    // 处理保存逻辑
    console.log('保存表单数据:', formData);
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>添加健康卡</Text>
      </View>

      <View style={styles.form}>
        <View style={styles.formItem}>
          <Text style={styles.label}>姓名</Text>
          <TextInput
            style={styles.input}
            placeholder="请输入真实姓名"
            value={formData.name}
            onChangeText={(text) => setFormData({ ...formData, name: text })}
          />
        </View>

        <View style={styles.formItem}>
          <Text style={styles.label}>身份证号码</Text>
          <TextInput
            style={styles.input}
            placeholder="请输入身份证号码"
            value={formData.idNumber}
            onChangeText={(text) => setFormData({ ...formData, idNumber: text })}
          />
        </View>

        <TouchableOpacity style={styles.formItem}>
          <Text style={styles.label}>性别</Text>
          <View style={styles.rightContent}>
            <Text style={styles.placeholder}>必填</Text>
            <Text style={styles.arrow}>›</Text>
          </View>
        </TouchableOpacity>

        <TouchableOpacity style={styles.formItem}>
          <Text style={styles.label}>年龄</Text>
          <View style={styles.rightContent}>
            <Text style={styles.placeholder}>必填</Text>
            <Text style={styles.arrow}>›</Text>
          </View>
        </TouchableOpacity>

        <TouchableOpacity style={styles.formItem}>
          <Text style={styles.label}>与账户之间的关系</Text>
          <View style={styles.rightContent}>
            <Text style={styles.placeholder}>选择亲朋关系</Text>
            <Text style={styles.arrow}>›</Text>
          </View>
        </TouchableOpacity>

        <View style={styles.formItem}>
          <View style={styles.phonePrefix}>
            <Text>中国大陆 +86</Text>
          </View>
          <TextInput
            style={[styles.input, styles.phoneInput]}
            placeholder="请输入手机号码"
            value={formData.phone}
            onChangeText={(text) => setFormData({ ...formData, phone: text })}
            keyboardType="phone-pad"
          />
        </View>

        <View style={styles.switchItem}>
          <Text style={styles.label}>设为默认健康卡</Text>
          <Switch
            value={formData.isDefault}
            onValueChange={(value) => setFormData({ ...formData, isDefault: value })}
          />
        </View>

        <Text style={styles.tips}>用于接收预约信息及重要提醒，请谨慎填写</Text>

        <TouchableOpacity style={styles.saveButton} onPress={handleSave}>
          <Text style={styles.saveButtonText}>保存</Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  header: {
    padding: 15,
    backgroundColor: '#fff',
    borderBottomWidth: 1,
    borderBottomColor: '#eee',
  },
  headerTitle: {
    fontSize: 18,
    fontWeight: '500',
  },
  form: {
    backgroundColor: '#fff',
    paddingHorizontal: 15,
  },
  formItem: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingVertical: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#eee',
  },
  label: {
    fontSize: 16,
    color: '#333',
  },
  input: {
    flex: 1,
    marginLeft: 10,
    fontSize: 16,
    color: '#333',
  },
  rightContent: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  placeholder: {
    color: '#999',
    marginRight: 5,
  },
  arrow: {
    fontSize: 20,
    color: '#999',
  },
  phonePrefix: {
    paddingRight: 10,
    borderRightWidth: 1,
    borderRightColor: '#eee',
  },
  phoneInput: {
    marginLeft: 10,
  },
  switchItem: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingVertical: 15,
  },
  tips: {
    fontSize: 12,
    color: '#999',
    marginTop: 10,
  },
  saveButton: {
    backgroundColor: 'linear-gradient(90deg, #2196F3, #4CAF50)',
    borderRadius: 25,
    marginTop: 30,
    marginBottom: 20,
    paddingVertical: 12,
  },
  saveButtonText: {
    color: '#fff',
    textAlign: 'center',
    fontSize: 16,
    fontWeight: '500',
  },
});

export default HealthCard; 