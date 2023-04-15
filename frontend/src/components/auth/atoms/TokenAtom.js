import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const JWTState = atom({
    key: 'token',
    default: null,
    effects_UNSTABLE: [persistAtom],
})