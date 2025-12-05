// Hàm fetch dữ liệu ranh giới động từ backend và hiển thị lên bản đồ bằng Leaflet
async function fetchAndDisplayBoundaries() {
    const response = await fetch('/api/boundaries');
    const boundaries = await response.json();
    if (!Array.isArray(boundaries)) return;

    // Xóa các lớp cũ nếu có
    if (window.dynamicBoundaryLayer) {
        map.removeLayer(window.dynamicBoundaryLayer);
    }
    window.dynamicBoundaryLayer = L.layerGroup();

    boundaries.forEach(item => {
        // Giả sử geometry là GeoJSON hoặc WKT, ở đây demo với GeoJSON
        let geojson;
        try {
            geojson = JSON.parse(item.geometry);
        } catch {
            // Nếu là WKT thì cần chuyển đổi sang GeoJSON (cần thư viện wkt)
            return;
        }
        const layer = L.geoJSON(geojson, {
            style: {
                color: item.type === 'old' ? '#ff9800' : '#28a745',
                weight: 2,
                dashArray: item.type === 'old' ? '5,5' : null,
                fillOpacity: 0.3
            }
        });
        // Popup chi tiết
        let popupHtml = `<div class='popup-title'>${item.unit ? item.unit.nameNew : ''}</div>`;
        popupHtml += `<div class='popup-info'><strong>Loại:</strong> ${item.type === 'old' ? 'Ranh giới cũ' : 'Ranh giới mới'}</div>`;
        popupHtml += `<div class='popup-info'><strong>Thời gian:</strong> ${item.time || ''}</div>`;
        if (item.unit) {
            popupHtml += `<div class='popup-info'><strong>Tên cũ:</strong> ${item.unit.nameOld || ''}</div>`;
            popupHtml += `<div class='popup-info'><strong>Diện tích:</strong> ${item.unit.area || ''} km²</div>`;
            popupHtml += `<div class='popup-info'><strong>Dân số:</strong> ${item.unit.population || ''}</div>`;
        }
        if (item.unit && item.unit.mergeDecisions && item.unit.mergeDecisions.length > 0) {
            popupHtml += `<div class='popup-info'><strong>Quyết định sáp nhập:</strong><ul style='margin:0 0 0 15px;padding:0;'>`;
            item.unit.mergeDecisions.forEach(dec => {
                popupHtml += `<li>Số: ${dec.number}, Ngày: ${dec.date || ''}, Ảnh hưởng: ${dec.effect || ''}</li>`;
            });
            popupHtml += `</ul></div>`;
        }
        if (item.unit && item.unit.boundaries && item.unit.boundaries.length > 0) {
            popupHtml += `<div class='popup-info'><strong>Mốc giới:</strong> ${item.unit.boundaries.length} đoạn</div>`;
        }
        // Có thể bổ sung thêm thông tin ảnh hưởng, cơ sở hạ tầng, v.v.
        layer.bindPopup(popupHtml);
        window.dynamicBoundaryLayer.addLayer(layer);
    });
    window.dynamicBoundaryLayer.addTo(map);
}

// Gọi hàm này khi load trang hoặc khi cần cập nhật dữ liệu

fetchAndDisplayBoundaries();

// Hàm fetch thống kê tổng quan và cập nhật giao diện
async function fetchAndDisplayStatistics() {
    const response = await fetch('/api/statistics/summary');
    const stats = await response.json();
    if (!Array.isArray(stats)) return;

    // Thống kê số xã/huyện bị sáp nhập, tổng diện tích, dân số
    let xaCount = 0, huyenCount = 0, areaTotal = 0, popTotal = 0;
    stats.forEach(item => {
        if (item.type === 'xa' && item.status === 'new') xaCount++;
        if (item.type === 'huyen' && item.status === 'new') huyenCount++;
        areaTotal += item.area || 0;
        popTotal += item.population || 0;
    });

    // Cập nhật giao diện thống kê
    const xaElem = document.querySelector('.stat-card .number');
    if (xaElem) xaElem.textContent = xaCount + ' xã';
    const huyenElem = document.querySelectorAll('.stat-card .number')[1];
    if (huyenElem) huyenElem.textContent = huyenCount + ' huyện';
    // Có thể bổ sung thêm thống kê diện tích, dân số nếu muốn
}

fetchAndDisplayStatistics();
