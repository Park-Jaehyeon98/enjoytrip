const modalStore = {
  namespaced: true,
  state: {
    showModal: false,
    modalType: "",
  },
  getters: {
    showModal: function (state) {
      return state.showModal;
    },
    showModalType: function (state) {
      return state.modalType;
    },
  },
  mutations: {
    SET_SHOW_MODAL(state, showModal) {
      state.showModal = showModal;
    },
    SET_MODAL_TYPE(state, modalType) {
      state.modalType = modalType;
    },
  },
  actions: {},
};

export default modalStore;
