import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const authenticationState = atom({
    key: 'isAuthenticated',
    default: false,
    effects_UNSTABLE: [persistAtom],
})