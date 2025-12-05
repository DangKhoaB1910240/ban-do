// Thêm timeline slider và overlay so sánh
// Yêu cầu: Thêm HTML <input type="range" id="timelineSlider"> vào file HTML


// Timeline slider: fetch boundaries theo năm
async function fetchBoundariesByYear(year) {
    const response = await fetch(`/api/boundaries?year=${year}`);
    const boundaries = await response.json();
    if (!Array.isArray(boundaries)) return;
    if (window.dynamicBoundaryLayer) map.removeLayer(window.dynamicBoundaryLayer);
    window.dynamicBoundaryLayer = L.layerGroup();
    boundaries.forEach(item => {
        let geojson;
        try { geojson = JSON.parse(item.geometry); } catch { return; }
        const layer = L.geoJSON(geojson, {
            style: {
                color: item.type === 'old' ? '#ff9800' : '#28a745',
                weight: 2,
                dashArray: item.type === 'old' ? '5,5' : null,
                fillOpacity: 0.3
            }
        });
        layer.bindPopup(`<div class='popup-title'>${item.unit ? item.unit.nameNew : ''}</div>`);
        window.dynamicBoundaryLayer.addLayer(layer);
    });
    window.dynamicBoundaryLayer.addTo(map);
}

document.addEventListener('DOMContentLoaded', function() {
    const slider = document.getElementById('timelineSlider');
    if (slider) {
        slider.addEventListener('input', function() {
            fetchBoundariesByYear(this.value);
        });
    }
});

// Overlay highlight vùng thay đổi (demo: highlight vùng có sự khác biệt giữa 2 năm)
async function highlightChangedAreas(year1, year2) {
    const res1 = await fetch(`/api/boundaries?year=${year1}`);
    const res2 = await fetch(`/api/boundaries?year=${year2}`);
    const b1 = await res1.json();
    const b2 = await res2.json();
    // So sánh và highlight vùng khác biệt (cần backend hỗ trợ hoặc so sánh trên frontend nếu dữ liệu nhỏ)
    // ...
}

// Tìm kiếm nâng cao
async function advancedSearch(query, type, status, minArea, maxArea) {
    const params = new URLSearchParams({query, type, status, minArea, maxArea});
    const response = await fetch(`/api/units/search?${params.toString()}`);
    return await response.json();
}

// Thống kê động, phân tích tác động
async function fetchImpactAnalysis() {
    const response = await fetch('/api/analysis/boundary-change');
    return await response.json();
}

// Quản lý mốc giới
async function fetchBoundaryPoints(unitId) {
    const response = await fetch(`/api/boundary-points?unitId=${unitId}`);
    return await response.json();
}

// Xuất báo cáo PDF/Excel
function exportReport(format = 'csv') {
    window.open(`/api/report/export-${format}`);
}
