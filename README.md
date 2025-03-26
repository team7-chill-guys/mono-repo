MSA 기반 물류 관리 시스템 "물류ON"

<img src="https://github.com/user-attachments/assets/5abb22ef-cf31-4bf9-942e-c216eb58b56b" alt="서비스 소개" width="100%"/>


<br/>
<br/>

# 1. About Project
- 프로젝트명: MSA 기반 국내 물류 관리 및 배송 시스템 개발
- 프로젝트 목표:
    - 기존 모놀리식 시스템의 한계를 극복하기 위해 MSA 구조 도입, 각 도메인을 독립적으로 설계
    - DDD 관점 적용하여 물류 시스템을 체계적으로 모델링 및 개발
    - 실제 운영 환경에서도 활용 가능한 구조를 갖춘 시스템 구현
- 진행 기간: 2025년 4월 11일 ~ 4월 25일 (총 15일간)

<br/>
<br/>

# 2. Team7 Chill Guys
| 김소진 | 박소해 | 오혜민 | 조규성 | 주소연 |
|:------:|:------:|:------:|:------:|:------:|
| <img src="https://avatars.githubusercontent.com/sojinnuna" alt="김소진" width="150"> | <img src="https://avatars.githubusercontent.com/S2gamzaS2" alt="박소해" width="150"> | <img src="https://avatars.githubusercontent.com/oh-min" alt="오혜민" width="150"> | <img src="https://avatars.githubusercontent.com/Imnotcoderdude" alt="조규성" width="150"> | <img src="https://avatars.githubusercontent.com/jcowwk" alt="주소연" width="150"> |
| Lead | BE | BE | Tech | BE |
| [GitHub](https://github.com/sojinnuna) | [GitHub](https://github.com/S2gamzaS2) | [GitHub](https://github.com/oh-min) | [GitHub](https://github.com/Imnotcoderdude) | [GitHub](https://github.com/jcowwk) |

<br/>
<br/>

# 3. Tasks & Responsibilities (작업 및 역할 분담)
[조규성]
- **Eureka Server**:
    - 마이크로 서비스 등록 및 서비스 간 위치 탐색을 위한 등록 서버 구축
      
- **API Gateway**:
    - Spring Cloud Gateway 기반 API 게이트웨이 구성
    - 경로 기반 라우팅 설정 및 인증 필터
    - Eureka 연동을 통한 라우팅 자동화 구현
      
- **User Service**
    - 사용자 정보 등록, 조회, 수정, 권한 변경 등 사용자 기능 구현
    - JPA 기반 도메인 모델 설계 및 구현 (User, UserRole 등)
    - FeignClient를 통한 외부 마이크로서비스 통신 구조 설계

- **Auth Service**
    - 로그인 및 회원가입 기능 구현
    - JWT 기반 인증 로직 구현 (토큰 발급 및 검증)
    - Refresh Token 관리 및 재발급 로직 처리
    - Gateway와 연동하여 인증 필터 통과 시 토큰 기반 유저 정보 추출 처리

<br/>
<br/>

# 4. ERD
erdcloud : [https://www.erdcloud.com/d/sT8WDsAXXqfbB9Gay](https://www.erdcloud.com/d/JCRKQRxseMZY28368)
<br/>

<img src= https://github.com/user-attachments/assets/1427bac2-f2e2-4495-9910-e5e30c6e55a6>
<br/>
<br/>

# 5. Infra
<img src= https://github.com/user-attachments/assets/b274175e-5e61-461e-b4d9-6f2d1f69c674>

<br/>
<br/>

# 6. Technology Stack (기술 스택)

## 6.1 BackEnd
|  |  |  |
|-----------------|-----------------|-----------------|
| SpringBoot    |  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white" alt="SpringBoot" width="200"> | 3.4.3    |
| Java    |  <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white" alt="Java" width="200" > | 17 |
| Spring Data JPA    |  <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logo=SpringDataJPA&logoColor=white" alt="JPA" width="200" >    | 5.0.0  |
| Spring Security |  <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white" alt="QueryDSL" alt="QueryDSL" width="200">    | 3.4.2    |

<br/>
<br/>
