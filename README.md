# Firebase

Firebase에 호스팅해보기.

> 파이어베이스(Firebase)는 2011년 파이어베이스(Firebase, Inc)사가 개발하고 2014년 구글에 인수된 모바일 및 웹 애플리케이션 개발 플랫폼이다.
>
> wikipedia..

[Firebase](https://firebase.google.com/?hl=ko)

[Firebase Documentation](https://firebase.google.com/docs?hl=ko)

> Firebase 는 구글에서 제공하는 모바일 앱/웹을 위한 플랫폼이다. 
>
> Firebase는 인증, 데이터베이스, 스토리지, 푸시 알림, 호스팅, Function 등 여러 기능을 제공하기 때문에 개발자가 직접 일일이 기능을 개발할 필요가 없다. 
> 
> 백엔드 기능을 클라우드 서비스 형태로 제공하기 때문에 간단한 조작으로 서버리스 애플리케이션 개발이 가능하며, 서버를 구입할 필요도 없다. 

[Firestore 데이터 관리 방법 알아보기](https://velog.io/@khy226/Firestore-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0)

## Project

**프로젝트 추가**

[Firebase Console](https://console.firebase.google.com/) 에서 Firebase 프로젝트를 추가할 수 있다.

![Result](https://raw.githubusercontent.com/jihunparkme/firebase-example/main/img/1-generate-project.png)

.

**비공개 키 생성**

Spring-boot 와 연동을 위해 비공개 키 생성이 필요하다.

프로젝트 설정으로 먼저 이동해보자.

![Result](https://raw.githubusercontent.com/jihunparkme/firebase-example/main/img/2-project-setting.png)

.

프로젝트에 대한 여러 설정이 나오는데, 서비스 계정 탭을 보면 새 비공개 키 생성 버튼이 있다.

![Result](https://raw.githubusercontent.com/jihunparkme/firebase-example/main/img/3-generate-private-key.png)

여기서 생성된 .json 파일의 비공개 키를 Spring-boot 프로젝트의 resources 디렉토리 경로에 넣어주자.

.

**Cloud Firestore 데이터베이스 만들기**

> Firestore 는 firebase 에서 지원하는 NoSQL 데이터베이스 서비스로, 실시간 리스너를 통해 사용자와 기기간 데이터의 실시간 동기화가 가능하다. 
> 
> 또한, Cloud Firestore는 앱에서 많이 사용되는 데이터를 캐시하기 때문에 기기가 오프라인 상태가 되더라도 앱에서 데이터를 쓰고 읽고 수신 대기하고 쿼리할 수 있다.

[Firestore 데이터 관리 방법 알아보기](https://velog.io/@khy226/Firestore-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0)

.

데이터베이스 생성도 간단하다.

[Cloud Firestore](https://firebase.google.com/docs/firestore?hl=ko) 는 NoSQL 클라우드 데이터베이스를 사용한다.

데이터 추가를 위해 데이터베이스 생성 후 컬렉션, 문서, 필드를 추가해주면 된다.

![Result](https://raw.githubusercontent.com/jihunparkme/firebase-example/main/img/4-cloud-firestore.png)

# Spring Boot

이제 본격적으로 Firebase 와 Spring-boot 를 연동해보자.

- spring-boot '3.1.4'
- java '17'

## Dependency

[Firebase MVN Repository](https://mvnrepository.com/artifact/com.google.firebase/firebase-admin)

```groovy
implementation 'com.google.firebase:firebase-admin:9.2.0'
```

## Firebase 연동

FileInputStream 을 통해 비공개 키 파일을 읽고, FirebaseApp 을 초기화하여 연동을 할 수 있다.

```java
@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/config/key/serviceAccountKey.json");
            FirebaseOptions.Builder optionBuilder = FirebaseOptions.builder();
            FirebaseOptions options = optionBuilder
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 데이터 조회

### 데이서 수정

https://suinchoi.tistory.com/27

### 데이터 삭제

https://cloud.google.com/firestore/docs/manage-data/delete-data?hl=ko#java_2

### 데이터 추가

https://cloud.google.com/firestore/docs/manage-data/add-data?hl=ko#javaandroid

## Hosting

## Reference

- https://hayeondev.gatsbyjs.io/220125-firestore-react-chat-app/