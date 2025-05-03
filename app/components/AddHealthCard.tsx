import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Switch,
  SafeAreaView,
} from 'react-native';

const AddHealthCard = () => {
  const [formData, setFormData] = useState({
    name: '',
    idNumber: '',
    gender: '',
    age: '',
    relationship: '',
    phoneNumber: '',
    isDefault: false,
    agreedToTerms: false,
  });

  const handleSave = () => {
    // 处理表单提交
    console.log('Form data:', formData);
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity style={styles.backButton}>
          <Text>{'<'}</Text>
        </TouchableOpacity>
        <Text style={styles.title}>添加健康卡</Text>
        <TouchableOpacity style={styles.moreButton}>
          <Text>⋮</Text>
        </TouchableOpacity>
      </View>

      <View style={styles.form}>
        <View style={styles.inputGroup}>
          <Text style={styles.label}>姓名</Text>
          <TextInput
            style={styles.input}
            placeholder="请输入真实姓名"
            value={formData.name}
            onChangeText={(text) => setFormData({...formData, name: text})}
          />
        </View>

        <View style={styles.inputGroup}>
          <Text style={styles.label}>身份证号码</Text>
          <TextInput
            style={styles.input}
            placeholder="请输入身份证号码"
            value={formData.idNumber}
            onChangeText={(text) => setFormData({...formData, idNumber: text})}
          />
        </View>

        <TouchableOpacity style={styles.inputGroup}>
          <Text style={styles.label}>性别</Text>
          <View style={styles.selectButton}>
            <Text style={styles.selectText}>必填</Text>
            <Text style={styles.arrow}>{'>'}</Text>
          </View>
        </TouchableOpacity>

        <TouchableOpacity style={styles.inputGroup}>
          <Text style={styles.label}>年龄</Text>
          <View style={styles.selectButton}>
            <Text style={styles.selectText}>必填</Text>
            <Text style={styles.arrow}>{'>'}</Text>
          </View>
        </TouchableOpacity>

        <TouchableOpacity style={styles.inputGroup}>
          <Text style={styles.label}>与账户之间的关系</Text>
          <View style={styles.selectButton}>
            <Text style={styles.selectText}>选择亲朋关系</Text>
            <Text style={styles.arrow}>{'>'}</Text>
          </View>
        </TouchableOpacity>

        <View style={styles.inputGroup}>
          <Text style={styles.label}>中国大陆 +86</Text>
          <TextInput
            style={styles.input}
            placeholder="请输入手机号码"
            value={formData.phoneNumber}
            onChangeText={(text) => setFormData({...formData, phoneNumber: text})}
            keyboardType="phone-pad"
          />
        </View>

        <View style={styles.switchContainer}>
          <Text style={styles.label}>设为默认健康卡</Text>
          <Switch
            value={formData.isDefault}
            onValueChange={(value) => setFormData({...formData, isDefault: value})}
          />
        </View>

        <View style={styles.termsContainer}>
          <Switch
            value={formData.agreedToTerms}
            onValueChange={(value) => setFormData({...formData, agreedToTerms: value})}
          />
          <Text style={styles.termsText}>
            我已阅读并同意协议《康力特每个人信息保护权》
          </Text>
        </View>

        <TouchableOpacity 
          style={[styles.saveButton, !formData.agreedToTerms && styles.saveButtonDisabled]}
          onPress={handleSave}
          disabled={!formData.agreedToTerms}
        >
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
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    padding: 16,
    backgroundColor: '#fff',
  },
  backButton: {
    padding: 8,
  },
  title: {
    fontSize: 18,
    fontWeight: '500',
  },
  moreButton: {
    padding: 8,
  },
  form: {
    padding: 16,
  },
  inputGroup: {
    backgroundColor: '#fff',
    padding: 16,
    marginBottom: 1,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  label: {
    fontSize: 16,
    color: '#333',
  },
  input: {
    flex: 1,
    marginLeft: 16,
    fontSize: 16,
    color: '#666',
  },
  selectButton: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  selectText: {
    color: '#999',
    marginRight: 8,
  },
  arrow: {
    color: '#999',
  },
  switchContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    backgroundColor: '#fff',
    padding: 16,
    marginTop: 16,
  },
  termsContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: 16,
    paddingHorizontal: 16,
  },
  termsText: {
    marginLeft: 8,
    fontSize: 14,
    color: '#666',
  },
  saveButton: {
    backgroundColor: '#2196F3',
    borderRadius: 24,
    padding: 16,
    alignItems: 'center',
    marginTop: 32,
    marginHorizontal: 16,
  },
  saveButtonDisabled: {
    opacity: 0.5,
  },
  saveButtonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: '500',
  },
});

export default AddHealthCard; 