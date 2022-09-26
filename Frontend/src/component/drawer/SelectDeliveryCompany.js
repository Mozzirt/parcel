import './SelectDeliveryCompany.scss';

const Cards = () => {
  const data = [
      {
        key: 1,
        src: '/asset/ci/rectangle/Group 34316.svg',
        name: '로젠택배'        
      },
      {
        key: 2,
        src: '/asset/ci/rectangle/Group 34318.svg',
        name: 'CJ대한통운'        
      },
      {
        key: 3,
        src: '/asset/ci/rectangle/Group 34319.svg',
        name: '롯데택배'       
      },
      {
        key: 4,
        src: '/asset/ci/rectangle/Group 34320.svg',
        name: '우체국'        
      },
      {
        key: 5,
        src: '/asset/ci/rectangle/Group 34321.svg',
        name: '경동택배'        
      },
      {
        key: 6,
        src: '/asset/ci/rectangle/Group 34322.svg',
        name: '쿠팡'        
      },
      {
        key: 7,
        src: '/asset/ci/rectangle/image 22.svg',
        name: '마켓컬리'        
      },
      {
        key: 8,
        src: '/asset/ci/rectangle/image 10.svg',
        name: '한진택배'        
      },
  ]

  return (
      <div className="delivery-company-container">
        <div className='card-list'>
          {
              data.map((item, index) => (
                <div className='card-item' key={index}>
                  <div className='delivery-ci-container'>
                    <img src={item.src} alt={item.name} />
                  </div>
                  <div className='card-name'>
                    {item.name}
                  </div>
                </div>
              ))
          }
        </div>
      </div>
  )
}

function SelectDeliveryCompany() {
  return (
    <div className='drawer-root-container'>
      <div className='wrapper'>
        <div className='drawer-title'>
          택배사 선택
        </div>
        <div className='button close'>
          <img src='/asset/close_modal.svg' alt='close'></img>
        </div>

        <div className='delivery-division-box button'>
          <div className='delivery-division-tab active'>
            국내택배
          </div>
          <div className='delivery-division-tab'>
            쇼핑몰택배
          </div>
          <div className='delivery-division-tab'>
            해외택배
          </div>
        </div>

        <Cards />

        <div className='button button-container'>
          <div className='button-name'>
            배송조회
          </div>
        </div>
      </div>
    </div>
  );
}

export default SelectDeliveryCompany;
