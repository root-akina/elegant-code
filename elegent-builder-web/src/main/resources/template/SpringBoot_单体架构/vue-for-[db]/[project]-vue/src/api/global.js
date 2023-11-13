//上传文件路径
const uploadAction = '/file/file/up-load';
//上传文件路径
const uploadPartAction = '/file/file/up-load-part';
//系统code
const systemCode = 'project-web';
//系统clientSecret
const clientSecret = 'pass';
//通道
const channelType = 'CHANNEL_TYPE';
//通道
const channelTypeAli = 'ALIYUN_SMS';
//通道
const channelTypeTencent = 'TENCENT_SMS';
//通道
const channelTypeBaidu = 'BAIDU_SMS';
//阿里云签名
const aliSignSource="ALI_SIGN_SOURCE";
const aliSmsType="ALI_TEMPLATE_TYPE";
//腾讯云签名
const tencentSignType = "TENCENT_SIGN_TYPE";
const tencentDocumentType = "TENCENT_DOCUMENT_TYPE";
const tencentInternationa="TENCENT_INTERNATIONAL";
const tencentSignPurpose="TENCENT_SIGN_PURPOSE";
const tencentSmstype ="TENCENT_SMS_TYPE";
//百度云签名
const baiduContentType = "BAIDU_DOCUMENT_TYPE";
const baiduCountryType="BAIDU_COUNTRY_TYPE";
const baiduSmstype="BAIDU_SMS_TYPE";
//通道标识
const channelLabel = 'CHANNEL_LABEL';
//审核状态
const auditStatus = 'AUDIT_STATUS';
const auditStatusInAudit = 'IN_AUDIT';
const auditStatusPassAudit = 'PASS_AUDIT';
const auditStatusFailAudit = 'FAIL_AUDIT';
//发送状态
const sendStatus = "SEND_STATUS";
const sendStatusYES="YES";
const sendStatusNO="NO";
//受理状态
const acceptStatus = 'ACCEPT_STATUS';
//受理状态:受理失败
const acceptStatusNo='NO';
//系统code
const dataStateYes = '0';
//系统code
const dataStateNo = '1';
//分类类型
const categoryType = 'CATEGORY_TYPE';
//性别code
const sex = 'SEX';
//默市区根节点
const defaultCity='1';
//资源类型code
const resourceType = 'RESOURCE_TYPE';
//是否叶子节点类型code
const leaf = 'LEAF';
//头像
const headPortrait ='HEAD_PORTRAIT';
//业务图片类型
const businessType = 'BUSINESS_TYPE';
//审核证明图片
const proofImage ='PROOF_IMAGE';
//企业状态
const status = 'STATUS';
//企业状态试用：TRIAL
const statusTrial = 'TRIAL';
//企业状态正式：OFFICIAL
const statusOfficial = 'OFFICIAL';
//企业状态停用：STOP
const statusStop = 'STOP';
//企业状态拒绝：REFUSE
const statusRefuse= 'REFUSE';

//请求方式
const requestMethod= 'REQUEST_METHOD';

export default {
  uploadAction,
  uploadPartAction,
  systemCode,
  clientSecret,
  channelType,
  channelLabel,
  channelTypeAli,
  channelTypeTencent,
  channelTypeBaidu,
  sendStatus,
  sendStatusYES,
  sendStatusNO,
  dataStateYes,
  dataStateNo,
  auditStatus,
  auditStatusInAudit,
  auditStatusPassAudit,
  auditStatusFailAudit,
  acceptStatus,
  acceptStatusNo,
  aliSignSource,
  tencentSignType,
  tencentDocumentType,
  tencentInternationa,
  tencentSignPurpose,
  tencentSmstype,
  baiduSmstype,
  baiduContentType,
  baiduCountryType,
  aliSmsType,
  sex,
  resourceType,
  leaf,
  defaultCity,
  categoryType,
  headPortrait,
  proofImage,
  businessType,
  status,
  statusTrial,
  statusOfficial,
  statusStop,
  statusRefuse,
  requestMethod
}
