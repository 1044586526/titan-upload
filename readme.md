# 大文件断点续传组件

titan-upload Component 是一个大文件断点续传组件，支持大文件的上传。
## 项目的架构和设计

titan-upload Component 是一个大文件断点续传组件，它的设计和架构主要考虑了大文件上传和断点续传的需求。

### 总体架构

项目主要由三个部分组成：文件元数据存储、文件上传预处理和文件上传。

- **文件元数据存储**：我们使用 `FileMetadataProperties` 类来存储文件的元数据，包括存储类型、元数据文件目录、临时文件目录和上传文件目录。这些信息可以配置在 `application.yml` 文件中。

- **文件上传预处理**：在文件上传之前，我们首先进行预处理，获取文件的 `sha256` 和 `totalBytes`。这一步由 `FileController` 的 `preprocessFileUpload` 方法实现。

- **文件上传**：文件上传由 `FileController` 的 `uploadFile` 方法实现。它接收文件的 `sha256`，`file`，`startByte` 和 `totalBytes`，然后调用 `FileService` 来完成文件的上传。

### 设计理念

我们的设计理念是使大文件上传更简单、更可靠。通过使用断点续传，我们可以在网络不稳定或者其他原因导致上传中断时，从断点处继续上传，而不是重新上传整个文件。

### 关键技术决策

我们选择 Spring Boot 作为我们的主要框架，因为它可以简化 Java 应用的开发和部署。我们使用 Maven 来管理项目的依赖，确保项目的构建过程简单可靠。我们还使用了 JavaScript 和 SQL，分别用于前端的文件上传和后端的文件元数据存储。

## 主要特性

- 大文件上传
- 断点续传
- 文件元数据存储

## 技术栈

- Java
- Maven
- JavaScript
- Spring Boot
- SQL


## 如何使用

1. 克隆此仓库到本地
2. 使用 IntelliJ IDEA 打开项目
3. 运行 Maven 命令 `mvn clean install` 来构建项目

## 集成指南

`titan-upload-spring-boot-starter`是一个Spring Boot Starter，可以帮助您快速集成titan-upload组件到您的Spring Boot应用中。

在您的`pom.xml`文件中，添加以下依赖：

```xml
<dependency>
    <groupId>cn.quant_cloud</groupId>
    <artifactId>titan-upload-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### 参数配置
在您的`application.yml`文件中，添加以下配置：

```yaml
file:
  metadata:
    # 元数据存储类型：支持MYSQL和本地存储（MYSQL默认存储表名为file_metadata）
    # 如果不配置，识别是否有注入DataSource，如果有则使用MYSQL存储，否则使用本地存储
    storageType: MYSQL
    # 临时文件存储路径（必填）
    tempDir: /tmp/titan-upload
    # 上传文件存储路径（必填）
    uploadDir: /tmp/titan-upload/upload
    # 文件元数据存储路径
    # 如果不配置，则在用户的主目录下创建一个隐藏目录.file_metadata作为默认路径
    metadataDir: /tmp/titan-upload/metadata 
``` 

### 上传接口支持
当您引入`titan-upload-spring-boot-starter`后，会自动注册一个文件预请求和上传接口
`FileController`提供了两个主要的接口，用于处理文件的预处理和上传。

#### 一、文件预处理接口

此接口用于预处理文件上传，需要提供文件的`sha256`和`totalBytes`。

请求方法：GET
请求路径：`/file/preprocess`
请求参数：
- `sha256`：文件的SHA256哈希值，不能为空
- `totalBytes`：文件的总字节数

返回值：预处理文件上传的响应信息，包括是否需要上传，已上传的字节数等。

#### 二、文件上传接口

此接口用于上传文件，需要提供文件的`sha256`，`file`，`startByte`和`totalBytes`。

请求方法：POST
请求路径：`/file/upload`
请求参数：
- `sha256`：文件的SHA256哈希值，不能为空
- `file`：需要上传的文件，不能为空
- `startByte`：文件的起始字节
- `totalBytes`：文件的总字节数

返回值：文件上传的结果，成功或失败。



## 前端代码实现参考
### 上传
在titan-upload/titan-upload-core/src/main/resources/html/index.html中，实现了一个简单的上传页面，可以参考该页面实现上传功能。

在 HTML 页面中，选择文件并点击 "Start Upload" 按钮开始上传。上传过程中可以随时暂停并在稍后恢复。

```html
<div id="app">
    <input type="file" @change="handleFileChange" />
    <button @click="startUpload">Start Upload</button>
</div>
```
